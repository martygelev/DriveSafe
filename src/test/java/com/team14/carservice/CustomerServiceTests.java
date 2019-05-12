package com.team14.carservice;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.UserDto;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.service.CustomerServiceImpl;
import com.team14.carservice.service.common.CustomerCarService;
import com.team14.carservice.service.common.EmailService;
import com.team14.carservice.service.common.PassService;
import com.team14.carservice.service.vehicles.CustomerCarServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Optional;

import static com.team14.carservice.Helpers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CustomerServiceTests {
   
   private static final Customer CUSTOMER = customerMaker();
   private static final UserDto USER_DTO = userDtoMaker();
   private static final User USER = userMaker();
   
   @Mock
   private CustomerRepository mockCustomerRepository;
   @Mock
   private UserDetailsManager userDetailsManager;
   @Mock
   private PasswordEncoder passwordEncoder;
   @Mock
   private PassService passService;
   @Mock
   private CustomerCarService carService;
   @Mock
   private EmailService emailService;
   @Mock
   private JavaMailSender emailSender;
   
   @InjectMocks
   CustomerServiceImpl customerService;
   @InjectMocks
   CustomerCarServiceImpl customerCarService;
   
   @Before
   public void loadContextHolder() {
      
      Authentication authentication = Mockito.mock(Authentication.class);
      SecurityContext securityContext = Mockito.mock(SecurityContext.class);
      Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
      SecurityContextHolder.setContext(securityContext);
      when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("test");
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getByIdThrowsWhenNotFound() {
      customerService.getById(1);
   }
   
   @Test
   public void getByIdReturnsWhenFound() {
      
      when(mockCustomerRepository.findById(1)).thenReturn(Optional.of(CUSTOMER));
      
      Assert.assertEquals(CUSTOMER, customerService.getById(1));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getByEmailThrowsWhenNotFound() {
      customerService.getByEmail("test");
   }
   
   @Test
   public void getByEmailReturnsWhenFound() {
      
      when(mockCustomerRepository.findByEmail("test")).thenReturn(CUSTOMER);
      
      Assert.assertEquals(CUSTOMER, customerService.getByEmail("test"));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createCustomerThrowsWhenCustomerExists() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(CUSTOMER);
      
      customerService.createCustomer(USER_DTO);
   }
   
   @Test
   public void createCustomerGeneratesNewPasswordWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(null);
      
      when(passService.generateRandomPassword()).thenReturn("randomPassword");
      
      when(passwordEncoder.encode("randomPassword")).thenReturn("encodedPassword");
      
      customerService.createCustomer(USER_DTO);
      
      Mockito.verify(passService, Mockito.times(1)).generateRandomPassword();
   }
   
   @Test
   public void createCustomerEncodesPasswordWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(null);
      
      when(passService.generateRandomPassword()).thenReturn("randomPassword");
      
      when(passwordEncoder.encode("randomPassword")).thenReturn("encodedPassword");
      
      customerService.createCustomer(USER_DTO);
      
      Mockito.verify(passwordEncoder, Mockito.times(1)).encode("randomPassword");
   }
   
   @Test
   public void createCustomerGeneratesAndEncodesPasswordWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(null);
      
      when(passService.generateRandomPassword()).thenReturn("randomPassword");
      
      when(passwordEncoder.encode("randomPassword")).thenReturn("encodedPassword");
      
      customerService.createCustomer(USER_DTO);
      
      Mockito.verify(passwordEncoder, Mockito.times(1)).encode("randomPassword");
      Mockito.verify(passService, Mockito.times(1)).generateRandomPassword();
      
   }
   
   @Test
   public void createCustomerConstructsNewUserWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(null);
      
      when(passService.generateRandomPassword()).thenReturn("randomPassword");
      
      when(passwordEncoder.encode("randomPassword")).thenReturn("encodedPassword");
      
      customerService.createCustomer(USER_DTO);
      
      Mockito.verify(userDetailsManager, Mockito.times(1)).createUser(USER);
   }
   
   @Test
   public void createCustomerCreatesNewCustomerWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(null);
      
      when(passService.generateRandomPassword()).thenReturn("randomPassword");
      
      when(passwordEncoder.encode("randomPassword")).thenReturn("encodedPassword");
      
      customerService.createCustomer(USER_DTO);
      
      Mockito.verify(mockCustomerRepository, Mockito.times(1)).save(any(Customer.class));
   }
   
   
   @Test
   public void createCustomerDoesNotCreateNewCustomerWhenCustomerExists() {
      
      when(mockCustomerRepository.findByEmail(USER_DTO.getEmail())).thenReturn(CUSTOMER);
      
      Mockito.verify(mockCustomerRepository, Mockito.times(0)).save(any(Customer.class));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void editCustomerThrowsWhenCustomerDoesNotExist() {
      
      when(mockCustomerRepository.findById(1)).thenReturn(null);
      
      customerService.editCustomer(2, "123", "test");
      
      Mockito.verify(mockCustomerRepository, Mockito.times(0)).save(any(Customer.class));
   }
   
   @Test
   public void editCustomerExecutesWhenCustomerExists() {
      
      when(mockCustomerRepository.findById(1)).thenReturn(Optional.of(CUSTOMER));
      
      customerService.editCustomer(1, "123", "test");
      
      Mockito.verify(mockCustomerRepository, Mockito.times(1)).save(any(Customer.class));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void updatePasswordThrowsWhenCustomerDoesNotExist() {
      
      UserDetails user = new User("username", "pass", new ArrayList<>());
      
      when(userDetailsManager.loadUserByUsername("test")).thenReturn(user);
      
      when(mockCustomerRepository.findByEmail(user.getUsername())).thenReturn(null);
      
      customerService.updatePassword("newPassword", "oldPassword");
      
      Mockito.verify(userDetailsManager, Mockito.times(0)).updateUser(any(User.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   @WithMockUser(username = "test", authorities = {"ADMIN", "USER"})
   public void updatePasswordThrowsWhenPasswordsDontMatch() {
      
      Customer customer = customerMaker();
      
      UserDetails user = new User("username", "pass", AuthorityUtils.createAuthorityList("ROLE_USER"));
      
      when(userDetailsManager.loadUserByUsername("test")).thenReturn(user);
      
      when(mockCustomerRepository.findByEmail(user.getUsername())).thenReturn(customer);
      
      customerService.updatePassword("newPassword", "oldPassword");
      
      Mockito.verify(userDetailsManager, Mockito.times(0)).updateUser(any(User.class));
      
   }
   
   @Test
   @WithMockUser(username = "test", authorities = {"ADMIN", "USER"})
   public void updatePasswordEncodesPasswordWhenValid() {
      
      Customer customer = customerMaker();
      
      UserDetails user = new User("username", "pass", AuthorityUtils.createAuthorityList("ROLE_USER"));
      
      when(userDetailsManager.loadUserByUsername("test")).thenReturn(user);
      
      when(mockCustomerRepository.findByEmail(user.getUsername())).thenReturn(customer);
      
      when(passwordEncoder.matches("pass", "pass")).thenReturn(true);
      
      
      customerService.updatePassword("newPassword", "pass");
      
      Mockito.verify(passwordEncoder, Mockito.times(1))
              .encode(anyString());
      
   }
   
   @Test
   @WithMockUser(username = "test", authorities = {"ADMIN", "USER"})
   public void updatePasswordExecutesWhenValid() {
      
      Customer customer = customerMaker();
      
      UserDetails user = new User("username", "pass", AuthorityUtils.createAuthorityList("ROLE_USER"));
      
      when(userDetailsManager.loadUserByUsername("test")).thenReturn(user);
      
      when(mockCustomerRepository.findByEmail(user.getUsername())).thenReturn(customer);
      
      when(passwordEncoder.matches("pass", "pass")).thenReturn(true);
      
      when(passwordEncoder.encode("newPassword")).thenReturn("newPassword");
      
      customerService.updatePassword("newPassword", "pass");
      
      Mockito.verify(userDetailsManager, Mockito.times(1))
              .changePassword("pass", "newPassword");
      
   }
   
   @Test
   public void getLoggedInUserCarsExecutes() {
      
      Customer customer = customerMaker();
      
      when(mockCustomerRepository.findByEmail("test")).thenReturn(customer);
      
      customerService.getLoggedInUserCars();
      
      verify(carService, times(1)).getAllByCustomerIdToDto(customer.getId());
   }
   
   @Test
   public void updateCustomerSaves() {
      
      UserDto dto = userDtoMaker();
      
      dto.setPassword("111111111111111");
      dto.setChangePassword("111111111111111");
      
      when(passwordEncoder.matches(dto.getPassword(), dto.getChangePassword())).thenReturn(true);
      
      when(userDetailsManager.loadUserByUsername(dto.getEmail())).thenReturn(userMaker());
      
      when(mockCustomerRepository.findByEmail(dto.getEmail())).thenReturn(customerMaker());
      
      customerService.updateCustomer(dto);
      
      verify(mockCustomerRepository, times(1)).save(any(Customer.class));
      
   }
   
   @Test(expected = NullPointerException.class)
   public void updateCustomerThrowsWhenCustomerDoesNotExist() {
      
      UserDto dto = userDtoMaker();
      
      dto.setPassword("111111111111111");
      dto.setChangePassword("111111111111111");
      
      when(passwordEncoder.matches(dto.getPassword(), dto.getChangePassword())).thenReturn(true);
      
      when(userDetailsManager.loadUserByUsername(dto.getEmail())).thenReturn(userMaker());
      
      when(mockCustomerRepository.findByEmail(dto.getEmail())).thenReturn(null);
      
      customerService.updateCustomer(dto);
      
      verify(mockCustomerRepository, times(1)).save(any(Customer.class));
      
   }
   
   @Test
   public void updateCustomerChangesPassword() {
      
      UserDto dto = userDtoMaker();
      
      dto.setPassword("111111111111111");
      dto.setChangePassword("111111111111111");
   
      when(userDetailsManager.loadUserByUsername(dto.getEmail())).thenReturn(userMaker());
      
      when(passwordEncoder.matches(dto.getPassword(), userMaker().getPassword())).thenReturn(true);
   
      when(passwordEncoder.encode(dto.getChangePassword())).thenReturn("pass");
      
      
      when(mockCustomerRepository.findByEmail(dto.getEmail())).thenReturn(customerMaker());
      
      customerService.updateCustomer(dto);
      
      verify(userDetailsManager, times(1)).changePassword(dto.getPassword(), "pass");
      
   }
}
