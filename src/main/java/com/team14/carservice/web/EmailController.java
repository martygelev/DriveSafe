package com.team14.carservice.web;

import com.team14.carservice.models.dto.SendEmailDto;
import com.team14.carservice.service.common.CustomerService;
import com.team14.carservice.service.common.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller()
public class EmailController {
   
   private final CustomerService customerService;
   private final EmailService emailService;
   
   @Autowired
   public EmailController(CustomerService customerService, EmailService emailService) {
      this.customerService = customerService;
      this.emailService = emailService;
   }
   
   @GetMapping("/emailPage")
   public String getEmailPage(Model model) {
      model.addAttribute("emailDto", new SendEmailDto());
      model.addAttribute("customers", customerService.getAllCustomerInfoDeletedFalse());
      return "admin/emailPage";
   }
   
   @PostMapping("/emailPage")
   public String sendEmail(@ModelAttribute SendEmailDto emailDto) {
      emailService.sendEmail(emailDto.getTo(), emailDto.getSubject(), emailDto.getText());
      return "redirect:/admin";
   }
}
