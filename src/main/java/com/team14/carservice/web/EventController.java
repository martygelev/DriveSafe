package com.team14.carservice.web;

import com.team14.carservice.models.RepairService;
import com.team14.carservice.service.common.RepairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/event")
public class EventController {
   
   private final RepairServiceService repairServiceService;
   
   @Autowired
   public EventController(RepairServiceService repairServiceService) {
      this.repairServiceService = repairServiceService;
   }
   
   @PreAuthorize("hasRole('ADMIN')")
   @GetMapping
   public ModelAndView newEvent() {
      ModelAndView model = new ModelAndView("admin/newEvent");
      
      List<RepairService> repairServiceList = repairServiceService.listAllRepairServices();
      
      model.addObject("repairServices", repairServiceList);
      return model;
   }

}
