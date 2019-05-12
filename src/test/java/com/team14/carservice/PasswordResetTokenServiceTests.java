package com.team14.carservice;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.PasswordResetToken;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.PasswordResetTokenRepository;
import com.team14.carservice.service.PasswordResetTokenServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.team14.carservice.Helpers.*;

@RunWith(MockitoJUnitRunner.class)
public class PasswordResetTokenServiceTests {
   
   @Mock
   PasswordResetTokenRepository repository;
   
   @Mock
   CustomerRepository customerRepository;
   @Mock
   UserDetailsManager userDetailsManager;
   
   @InjectMocks
   PasswordResetTokenServiceImpl passwordResetTokenService;
   
   
   @Test
   public void validatePasswordResetTokenThrowsWhenTokenIsNull() {
      
      Assert.assertEquals
              (passwordResetTokenService.validatePasswordResetToken
                      (null, null), "invalidToken");
      
   }
   
   @Test
   public void validatePasswordResetTokenThrowsWhenUserIsDifferent() {
      
      PasswordResetToken token = new PasswordResetToken();
      token.setExpiryDate(new Date());
      token.setCustomer(customerMaker());
      token.setId(1);
      token.setToken("testToken");
      
      Mockito.when(repository.findByToken("testToken")).thenReturn(token);
      
      Assert.assertEquals
              (passwordResetTokenService.validatePasswordResetToken
                      ("wrongUser", "testToken"), "invalidToken");
   }
   
   @Test
   public void validatePasswordResetTokenThrowsWhenTokenIsExpired() {
      
      PasswordResetToken token = tokenMaker();
      
      Date date = new Date();
      date.setTime(10);
      
      token.setExpiryDate(date);
      
      Mockito.when(repository.findByToken("testToken")).thenReturn(token);
      
      Assert.assertEquals
              (passwordResetTokenService.validatePasswordResetToken
                      (customerMaker().getEmail(), "testToken"), "expired");
   }
   
   @Test
   public void validatePasswordResetTokenExecutesWhenDateIsValid() {
      Customer customer = customerMaker();
      
      PasswordResetToken token = tokenMaker();
   
      Calendar today = Calendar.getInstance();
      Date todayDate = new Date();
      todayDate.setTime(today.getTime().getTime()+999999);
      token.setExpiryDate(todayDate);
      
      Mockito.when(repository.findByToken("testToken")).thenReturn(token);
      
      Mockito.when(userDetailsManager.loadUserByUsername(customer.getEmail()))
              .thenReturn(new User(customer.getEmail(), "pass", new ArrayList<>()));
   
      Assert.assertNull(passwordResetTokenService.validatePasswordResetToken
              (customerMaker().getEmail(), "testToken"));
   }
   
   @Test
   public void generateTokenGeneratesToken(){
      
      Mockito.when(customerRepository.findByEmail(Mockito.anyString())).thenReturn(Mockito.any(Customer.class));
      
      passwordResetTokenService.generateToken(userMaker());
      
      Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(PasswordResetToken.class));
   }
   
   @Test
   public void disablePreviousTokenExecutes(){
      PasswordResetToken token = tokenMaker();
      
      List<PasswordResetToken> list = new ArrayList<>();
      
      list.add(token);
   
      Calendar today = Calendar.getInstance();
      Date todayDate = new Date();
      todayDate.setTime(today.getTime().getTime()+999999);
      token.setExpiryDate(todayDate);
      
      Mockito.when(repository.findAllByCustomer_Id(1)).thenReturn(list);
      
      passwordResetTokenService.disablePreviousTokensForUser(1);
      
      Mockito.verify(repository, Mockito.times(1)).save(Mockito.any(PasswordResetToken.class));
   }
}
