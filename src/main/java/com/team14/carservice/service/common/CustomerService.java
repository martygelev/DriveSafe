package com.team14.carservice.service.common;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.CustomerDto;
import com.team14.carservice.models.dto.EmailAddressDto;
import com.team14.carservice.models.dto.UserDto;

import java.util.List;

public interface CustomerService {
   Customer getById(int id);
   
   List<EmailAddressDto> getAllCustomerInfoDeletedFalse();
   
   Customer getByEmail(String email);
   
   void createCustomer(UserDto userDto);
   
   void editCustomer(int userId, String number, String name);
   
   List<CustomerCarDto> getLoggedInUserCars();
   
   void updateCustomer(UserDto userDto);
   
   List<Customer> getAllCustomers();
   
   void editCustomer(Customer customer);
   
   List<CustomerDto> getAllCustomersToDto();
   
   void updatePassword(String newPassword, String oldPassword);
   
   void setNewPassword(String oldPassword, String newPassword);
   
   String createPasswordResetTokenForUser(String userEmail);
   
}
