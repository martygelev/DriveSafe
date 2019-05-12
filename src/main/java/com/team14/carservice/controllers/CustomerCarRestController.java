package com.team14.carservice.controllers;

import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.service.common.CustomerCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer-cars")
public class CustomerCarRestController {
   
   private final CustomerCarService service;
   
   @Autowired
   public CustomerCarRestController(CustomerCarService service) {
      this.service = service;
   }
   
   @GetMapping("/")
   public List<CustomerCar> getAll() {
      return service.getAll();
   }
   
   @GetMapping("/{customerId}")
   public List<CustomerCar> getByCustomer(@PathVariable Integer customerId) {
      return service.getByCustomerId(customerId);
   }
   
   @GetMapping("/dto")
   public List<CustomerCarDto> getByCustomerToDto(@RequestParam Integer customerId) {
      return service.getAllByCustomerIdToDto(customerId);
   }

}
