package com.team14.carservice.service.common;


import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
   void sendEmail(String to, String subject, String text);
   
   SimpleMailMessage constructEmail(String subject, String body, String to);
   
   void send(SimpleMailMessage message);
   
   SimpleMailMessage constructResetTokenEmail(String token, String userEmail);
   
   void sendNewPasswordEmail(String userEmail, String newPassword);
   
   void sendNewCustomerEmail(String username, String password);
   
   void sendEmailWithInvoice(String to, String subject, String body, byte[] invoicePdf);
}
