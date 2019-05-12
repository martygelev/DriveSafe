package com.team14.carservice.web;

import com.team14.carservice.models.dto.PasswordDto;
import com.team14.carservice.service.common.CustomerService;
import com.team14.carservice.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Locale;

@Controller
@RequestMapping("/user")
public class UserController {
   
   private final MessageSource messages;
   private final CustomerService customerService;
   
   @Autowired
   public UserController(@Qualifier("messageSource") MessageSource messages,
                         CustomerService customerService) {
      this.messages = messages;
      this.customerService = customerService;
   }
   
   //for user changing his password
   @PostMapping(value = "/updatePasswordUser")
   public ResponseEntity<?> changeUserPassword(
           @RequestParam("newpassword") String newPassword,
           @RequestParam("oldpassword") String oldPassword) {
      
      try {
         customerService.updatePassword(newPassword, oldPassword);
      } catch (ResponseStatusException e) {
         return new ResponseEntity<>(new GenericResponse(e.getMessage()), HttpStatus.NOT_FOUND);
      }
      
      return ResponseEntity.ok().body(new GenericResponse("Success updating password"));
   }
   
   //handles forgotten password
   @ResponseBody
   @PostMapping(value = "/savePassword")
   public GenericResponse savePassword(@Valid PasswordDto passwordDto) {
      
      if (passwordDto == null || passwordDto.getNewPassword() == null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "empty password");
      }
      
      User user = (User) SecurityContextHolder.getContext()
              .getAuthentication().getPrincipal();
      
      customerService.setNewPassword(user.getPassword(), passwordDto.getNewPassword());
      
      return new GenericResponse(
              messages.getMessage("message.resetPasswordSuc", null, Locale.ENGLISH));
      
   }
   
   
}
