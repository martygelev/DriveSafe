package com.team14.carservice;

import com.team14.carservice.service.EmailServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTests {
   
   @Mock
   JavaMailSender mailSender;
   
   @InjectMocks
   EmailServiceImpl emailService;
   
   @Test
   public void emailServiceSendsMail() {
      emailService.send(new SimpleMailMessage());
      
      Mockito.verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
   }
   
   @Test
   public void sendNewPasswordSendsPassword() {
      
      emailService.sendNewPasswordEmail("user", "pass");
      
      Mockito.verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
   }
   
   @Test
   public void sendNewCustomerEmailSendsEmail() {
      
      emailService.sendNewCustomerEmail("user", "pass");
      
      Mockito.verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
   }
   
}
