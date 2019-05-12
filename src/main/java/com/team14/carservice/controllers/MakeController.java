package com.team14.carservice.controllers;

import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.service.common.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/make")
public class MakeController {
   
   private final MakeService makeService;
   
   @Autowired
   public MakeController(MakeService makeService) {
      this.makeService = makeService;
   }
   
   @GetMapping
   public List<Make> getAllMakes() {
      return makeService.getAll();
   }
}
