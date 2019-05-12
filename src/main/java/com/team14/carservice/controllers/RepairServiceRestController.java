package com.team14.carservice.controllers;

import com.team14.carservice.models.RepairService;
import com.team14.carservice.service.common.RepairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/r-services")
public class RepairServiceRestController {
   
   private final RepairServiceService service;
   
   @Autowired
   public RepairServiceRestController(RepairServiceService service) {
      this.service = service;
   }
   
   @GetMapping
   public List<RepairService> listAllRepairServices() {
      return service.listAllRepairServices();
   }
   
   @GetMapping("/{id}")
   public RepairService getRepairServiceById(@PathVariable Integer id) {
      return service.getRepairServiceById(id);
   }
   
   @PostMapping("/new")
   public RepairService createNewRepairService(@RequestBody RepairService repairService) {
      
      RepairService repairService1 = new RepairService(repairService.getName(), repairService.getPrice());
      
      return service.createNewRepairService(repairService1);
   }
   
   @PutMapping("/delete")
   public void markRepairServiceDeleted(@RequestParam(value = "id") Integer id) {
   
      try {
         service.markServiceDeleted(id);
      }catch (Exception e){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
      }
   }
   
   @PutMapping("/edit")
   public RepairService editRepairService(@RequestBody RepairService serviceToEdit) {
      
      return service.editRepairService(serviceToEdit);
      
   }
}
