package com.team14.carservice.service;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.CustomerDto;
import com.team14.carservice.models.dto.EmailAddressDto;
import com.team14.carservice.models.dto.UserDto;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.service.common.*;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.team14.carservice.utils.UserIdentification.getLoggedInUserEmail;

@Service
public class CustomerServiceImpl implements CustomerService {
   
   private final PassService passService;
   private final EmailService emailService;
   private final PasswordEncoder passwordEncoder;
   private final CustomerRepository customerRepository;
   private final CustomerCarService customerCarService;
   private final UserDetailsManager userDetailsManager;
   private final PasswordResetTokenService passwordResetTokenService;
   
   @Autowired
   public CustomerServiceImpl(CustomerRepository customerRepository, CustomerCarService customerCarService,
                              PasswordResetTokenService passwordResetTokenService, UserDetailsManager userDetailsManager,
                              PassService passService, PasswordEncoder passwordEncoder, EmailService emailService) {
      this.customerRepository = customerRepository;
      this.customerCarService = customerCarService;
      this.passwordResetTokenService = passwordResetTokenService;
      this.userDetailsManager = userDetailsManager;
      this.passService = passService;
      this.passwordEncoder = passwordEncoder;
      this.emailService = emailService;
   }
   
   
   @Override
   public Customer getById(int customerId) {
      
      return customerRepository.findById(customerId).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND,
                      String.format("No customer with id %d found", customerId)));
      
   }
   
   @Override
   public List<CustomerCarDto> getLoggedInUserCars() {
      return customerCarService.getAllByCustomerIdToDto(getByEmail(getLoggedInUserEmail()).getId());
   }
   
   @Override
   public Customer getByEmail(String customerEmail) {
      
      Customer customer = customerRepository.findByEmail(customerEmail);
      
      if (customer == null)
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 String.format("No customer with name %s found", customerEmail));
      
      return customer;
   }
   
   @Override
   public List<Customer> getAllCustomers() {
      return customerRepository.findAll();
   }
   
   public List<EmailAddressDto> getAllCustomerInfoDeletedFalse() {
      return customerRepository.getAllCustomersEmails();
   }
   
   @Override
   public List<CustomerDto> getAllCustomersToDto() {
      return customerRepository.getAllCustomersToDto();
   }
   
   @Override
   public void createCustomer(UserDto userDto) {
      
      checkIfUserAlreadyExists(userDto.getEmail());
      
      try {
         constructNewUser(userDto);
      }catch (Exception e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending email.");
      }
      
      Customer newCustomer = new Customer(userDto.getEmail(), userDto.getPhone(), userDto.getName());
      
      customerRepository.save(newCustomer);
      
   }
   
   @Override
   public void editCustomer(int userId, String number, String name) {
      
      Customer customer = customerRepository.findById(userId).orElseThrow(() ->
              new ResponseStatusException(HttpStatus.NOT_FOUND,
                      "Customer does not exist!"));
      
      customer.setPhone(number);
      customer.setName(name);
      
      customerRepository.save(customer);
      
   }
   
   @Override
   public void editCustomer(Customer customer) {
      
      customerRepository.save(customer);
      
   }
   
   @Override
   public void updateCustomer(UserDto userDto) {
      
      String password = userDto.getPassword();
      String newPassword = userDto.getChangePassword();
      
      boolean passwordsMatch = passwordEncoder.matches(password,
              userDetailsManager.loadUserByUsername(userDto.getEmail()).getPassword());
      
      if (newPassword.length() > 0 && passwordsMatch) {
         String newEncodedPassword = passwordEncoder.encode(newPassword);
         userDetailsManager.changePassword(password, newEncodedPassword);
      }
      
      try {
         Customer customer = customerRepository.findByEmail(userDto.getEmail());
         customer.setName(userDto.getName());
         customer.setPhone(userDto.getPhone());
         customerRepository.save(customer);
         
      } catch (HibernateException he) {
         System.out.println(he.getMessage());
         throw he;
      }
   }
   
   @Override
   public void updatePassword(String newPassword, String oldPassword) {
      
      UserDetails user = userDetailsManager.loadUserByUsername(getLoggedInUserEmail());
      
      Customer customer = customerRepository.findByEmail(user.getUsername());
      
      if (customer == null) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no such user");
      }
      
      if (!validOldPassword(user, oldPassword)) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ("Incorrect old password."));
      }
      
      setNewPassword(oldPassword, newPassword);
      
      
   }
   
   @Override
   public void setNewPassword(String oldPassword, String newPassword) {
      
      userDetailsManager.changePassword(oldPassword, passwordEncoder.encode(newPassword));
      
   }
   
   
   @Override
   public String createPasswordResetTokenForUser(String userEmail) {
      
      UserDetails userDetails = userDetailsManager.loadUserByUsername(userEmail);
      
      User user = new User(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
      
      return passwordResetTokenService.generateToken(user);
      
   }
   
   private boolean validOldPassword(UserDetails user, String oldPassword) {
      return passwordEncoder.matches(oldPassword, user.getPassword());
   }
   
   private void checkIfUserAlreadyExists(String customerEmail) {
      if (customerRepository.findByEmail(customerEmail) != null) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
                 "User with that email is already in our system");
      }
   }
   
   private void constructNewUser(UserDto userDto) {
      
      String password = passService.generateRandomPassword();
      String encodedPassword = passwordEncoder.encode(password);
      
      List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
      User newUser = new User(userDto.getEmail(), encodedPassword, authorities);
      
      userDetailsManager.createUser(newUser);
      
      try {
         emailService.sendNewCustomerEmail(newUser.getUsername(), password);
      }catch (Exception e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while sending email.");
      }
      
   }
}
