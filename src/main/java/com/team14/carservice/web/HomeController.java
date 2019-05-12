package com.team14.carservice.web;

import com.team14.carservice.models.Customer;
import com.team14.carservice.repository.EventRepository;
import com.team14.carservice.service.common.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import static com.team14.carservice.utils.UserIdentification.getLoggedInUserEmail;

@Controller
public class HomeController {
   
   private final CustomerService customerService;
   
   private final EventRepository eventRepository;
   
   @Autowired
   public HomeController(CustomerService customerService, EventRepository eventRepository) {
      this.customerService = customerService;
      this.eventRepository = eventRepository;
   }
   
   @GetMapping("/")
   public String root() {
      return "login";
   }
   
   @GetMapping("/login")
   public String login() {
      return "login";
   }
   
   @PostMapping(value = "/login")
   public String loginPost() {
      return "redirect:/index";
   }
   
   @GetMapping("/access-denied")
   public String accessDenied() {
      return "/error/access-denied";
   }
   
   @GetMapping("/admin")
   public String admin() {
      return "admin/adminIndex";
   }
   
   @GetMapping("/me")
   public String user(Model model) {
        
        Customer customer = customerService.getByEmail(getLoggedInUserEmail());
        
        model.addAttribute("user", customer);
        model.addAttribute("visitsCount", eventRepository.countAllVisits(customer.getId()));
        model.addAttribute("notFinalizedCount", eventRepository.countNotFinalizedEvents(customer.getId()));
        
        
      return "user/userPage";
   }
   
  
   
}
