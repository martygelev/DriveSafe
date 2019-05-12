package com.team14.carservice.web;

import com.team14.carservice.models.RepairService;
import com.team14.carservice.service.common.RepairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/service")
public class ServiceController {
   
   private final RepairServiceService repairService;
   
   @Autowired
   public ServiceController(RepairServiceService repairService) {
      this.repairService = repairService;
   }
   
   
   @GetMapping
   public ModelAndView repairService(){
      
      ModelAndView model = new ModelAndView("admin/serviceList");
      
      List<RepairService> repairServiceList = repairService.listAllRepairServices();
      model.addObject("repairServices", repairServiceList);
      
      return model;
   }
}
