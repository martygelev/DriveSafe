package com.team14.carservice.controllers;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.CustomerDto;
import com.team14.carservice.models.dto.EmailAddressDto;
import com.team14.carservice.models.dto.UserDto;
import com.team14.carservice.service.common.CustomerService;
import com.team14.carservice.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/r-customers")
public class CustomerRestController {
   
   private final CustomerService service;
   private final MessageSource messages;
   
   @Autowired
   public CustomerRestController(@Qualifier("messageSource") MessageSource messages,
                                 CustomerService service) {
      this.service = service;
      this.messages = messages;
   }
   
   
   @GetMapping("/cars")
   public List<CustomerCarDto> getLoggedInUserCars() {
      
      return service.getLoggedInUserCars();
   }
   
   @GetMapping("/get")
   public List<CustomerDto> listAllCustomers() {
      return service.getAllCustomersToDto();
   }
   
   @GetMapping("/get/email")
   public List<EmailAddressDto> listAllCustomersEmails() {
      return service.getAllCustomerInfoDeletedFalse();
   }
   
   @GetMapping("/get/{id}")
   public Customer getById(@PathVariable int id) {
      try {
         return service.getById(id);
      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer with id %d was not found ", id));
      }
   }
   
   @PutMapping("/editInfo/{id}/{phone}/{name}")
   public void editCustomerInfo(@PathVariable int id, @PathVariable String phone, @PathVariable String name) {
      
      try {
         service.editCustomer(id, phone, name);
      } catch (ResponseStatusException e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                 String.format("Customer with id %d was not found ", id));
      }
   }
   
   @PutMapping("/edit}")
   public void updateCustomer(@RequestBody UserDto userDto) {
      service.updateCustomer(userDto);
   }
   
   @PostMapping("/new")
   public ResponseEntity<?> createCustomer(UserDto userDto) {
      
      try {
         service.createCustomer(userDto);
      } catch (ResponseStatusException r) {
         return new ResponseEntity<>(new GenericResponse(r.getMessage()), null, HttpStatus.BAD_REQUEST);
      } catch (DataIntegrityViolationException v) {
         return new ResponseEntity<>(new GenericResponse("Validation Error"), null, HttpStatus.BAD_REQUEST);
      } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<>(new GenericResponse("Unknown error"), null, HttpStatus.BAD_REQUEST);
      }
      
      return ResponseEntity.ok().body(new GenericResponse(messages.getMessage("message.regSucc",
              null, Locale.ENGLISH)));
      
   }
   
   @GetMapping("/get/{email}")
   public Customer getByEmail(@PathVariable String email) {
      try {
         return service.getByEmail(email);
      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No customer with username/email %s found!", email));
      }
   }
   
   @PutMapping("/remove")
   public void markCustomerDeleted(@RequestParam int customerId){
      
      Customer customer = service.getById(customerId);
      customer.setDeleted(true);
      service.editCustomer(customer);
   
   }
   
   @PutMapping("/restore")
   public void markCustomerNotDeleted(@RequestParam int customerId){
      
      Customer customer = service.getById(customerId);
      customer.setDeleted(false);
      service.editCustomer(customer);
      
   }
   
}
