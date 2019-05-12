package com.team14.carservice.web;

import com.team14.carservice.service.common.CustomerService;
import com.team14.carservice.service.common.EmailService;
import com.team14.carservice.service.common.PasswordResetTokenService;
import com.team14.carservice.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

@Secured("ANONYMOUS")
@Controller
@RequestMapping("/anonymous")
public class AnonymousUserController {
   
   private final MessageSource messages;
   private final EmailService emailService;
   private final CustomerService customerService;
   private final PasswordResetTokenService passwordResetTokenService;
   
   @Autowired
   public AnonymousUserController(@Qualifier("messageSource") MessageSource messages,
                                  EmailService emailService,
                                  CustomerService customerService,
                                  PasswordResetTokenService passwordResetTokenService) {
      this.messages = messages;
      this.emailService = emailService;
      this.customerService = customerService;
      this.passwordResetTokenService = passwordResetTokenService;
   }
   
   
   @RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
   public String forgottenPassword(Model model) {
      return "common/forgotPassword";
   }
   
   
   @ResponseBody
   @RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
   public ResponseEntity<?> resetPassword(@RequestParam("email") String userEmail) {
      
      String token;
      
      try {
         token = customerService.createPasswordResetTokenForUser(userEmail);
      } catch (UsernameNotFoundException e) {
         return new ResponseEntity<>(new GenericResponse(
                 messages.getMessage("message.badEmail",
                         null, Locale.ENGLISH)), null, HttpStatus.BAD_REQUEST);
      }
      
      emailService.send(emailService.constructResetTokenEmail(token, userEmail));
      
      return ResponseEntity.ok().body(new GenericResponse(
              messages.getMessage("message.resetPasswordEmail",
                      null, Locale.ENGLISH)));
      
   }
   
   @RequestMapping(value = "/changePassword", method = RequestMethod.GET)
   public String showChangePasswordPage(Model model,
                                        @RequestParam("username") String username,
                                        @RequestParam("token") String token) {
      
      String validationResult = passwordResetTokenService.validatePasswordResetToken(username, token);
      
      if (validationResult != null) {
         model.addAttribute("message",
                 messages.getMessage("auth.message." + validationResult, null, Locale.ENGLISH));
         
         return "redirect:/login";
      }
   
      passwordResetTokenService.disablePreviousTokensForUser(customerService.getByEmail(username).getId());
      
      return "common/updatePassword";
   }
   
}
