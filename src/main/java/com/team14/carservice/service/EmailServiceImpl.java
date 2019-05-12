package com.team14.carservice.service;

import com.team14.carservice.service.common.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
public class EmailServiceImpl implements EmailService {
   
   @Autowired
   @Qualifier("messageSource")
   private MessageSource messages;
   
   @Autowired
   private JavaMailSender emailSender;
   
   public EmailServiceImpl() {
   }
   
   @Autowired
   public EmailServiceImpl(@Qualifier("messageSource") MessageSource messages,
                           JavaMailSender emailSender) {

      this.emailSender = emailSender;
      this.messages = messages;
   }
   
   @Override
   public void send(SimpleMailMessage message) {
      try {
         emailSender.send(message);
      } catch (MailParseException | MailAuthenticationException | MailSendException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while parsing mail text.");
      }
   }
   
   @Override
   public void sendEmail(String to, String subject, String text) {
      send(constructEmail(subject, text, to));
   }
   
   @Override
   public SimpleMailMessage constructEmail(String subject, String body, String to) {
      
      SimpleMailMessage email = new SimpleMailMessage();
      
      email.setTo(to);
      email.setText(body);
      email.setSubject(subject);
      
      return email;
   }
   
   @Override
   public SimpleMailMessage constructResetTokenEmail(String token, String userEmail) {
      
      String url = "http://drivesafe-env.3wpbmmyfec.us-east-2.elasticbeanstalk.com/anonymous/changePassword?username=" +
              userEmail + "&token=" + token;
      
      String subject = messages.getMessage("message.resetPassword.email.subject",
              null, Locale.ENGLISH);
      
      String message = messages.getMessage("message.resetPassword.email.text",
              null, Locale.ENGLISH);
      
      return constructEmail(subject, message + " \r\n" + url, userEmail);
   }
   
   public void sendNewPasswordEmail(String userEmail, String newPassword) {
      String text = String.format("your new password: %s", newPassword);
      sendEmail(userEmail, "Password change", text);
   }
   
   public void sendEmailWithInvoice(String to, String subject, String body, byte[] content) {
      
      MimeMessage message = emailSender.createMimeMessage();
      try {
         MimeMessageHelper helper = new MimeMessageHelper(message, true);
         helper.setTo(to);
         helper.setSubject(subject);
         helper.setText(body);
         helper.addAttachment("DriveSafe Invoice.pdf", new ByteArrayResource(content));
         emailSender.send(message);
      } catch (MessagingException e) {

         e.printStackTrace();
      }
   }
   
   public void sendNewCustomerEmail(String userEmail, String password) {
      
      String text = String.format("Welcome to DriveSafe %s!" + System.lineSeparator() +
                      "Your randomly generated password is %s. You can log in and change it.",
              userEmail, password);
      
      sendEmail(userEmail, "Registration at Drive Safe Car Service", text);
   }
}
