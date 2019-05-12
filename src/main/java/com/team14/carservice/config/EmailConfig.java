package com.team14.carservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {

    private static final String REGISTRATION_EMAIL_MESSAGE =
                    "    <p style=\"margin-left: 5px;\">email</p>\n" +
                    "    <p style=\"margin-left: 5px\">password</p>\n";

    @Bean
    public SimpleMailMessage templateSimpleMessage() {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setText(REGISTRATION_EMAIL_MESSAGE);

        return message;
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        
        mailSender.setUsername("drivesafecarservice@gmail.com");
        mailSender.setPassword("Telerikteam14");
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return mailSender;
    }
}
