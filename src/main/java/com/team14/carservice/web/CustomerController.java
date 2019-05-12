package com.team14.carservice.web;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.EmailAddressDto;
import com.team14.carservice.models.dto.UserDto;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.service.common.CustomerCarService;
import com.team14.carservice.service.common.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/customers")
@ControllerAdvice
public class CustomerController {
   
   private final CustomerService customerService;
   private final CustomerCarService customerCarService;
   
   @Autowired
   public CustomerController(CustomerService customerService, CustomerCarService customerCarService) {
      this.customerService = customerService;
      this.customerCarService = customerCarService;
   }
   
   @GetMapping
   public ModelAndView getAllCustomersDeletedFalse() {
      ModelAndView model = new ModelAndView("admin/allCustomers");
      
      List<EmailAddressDto> customerList = customerService.getAllCustomerInfoDeletedFalse();
      
      Customer customer = new Customer();
      model.addObject("customer", customer);
      
      model.addObject("customers", customerList);
      
      return model;
   }
   
   @GetMapping("/all")
   public ModelAndView getAllCustomers() {
      ModelAndView model = new ModelAndView("admin/allCustomers");
      
      List<Customer> customerList = customerService.getAllCustomers();
      
      Customer customer = new Customer();
      model.addObject("customer", customer);
      
      model.addObject("customers", customerList);
      
      return model;
   }
   
   @GetMapping(value = "/{id}")
   public ModelAndView customerById(@PathVariable(value = "id") Integer id) {
      
      ModelAndView model = new ModelAndView("admin/customerInfo");
      
      Customer customer = customerService.getById(id);
      
      List<CustomerCar> cars = customerCarService.getByCustomerId(id);
      
      model.addObject("customer", customer);
      model.addObject("cars", cars);
      
      return model;
   }
   
   @ModelAttribute("user")
   public UserDto userRegistrationDto() {
      return new UserDto();
   }
   
   
   @GetMapping("/edit")
   public String getEditIndex(Model model, Principal principal) {
      
      UserDto userDto = new UserDto();
      Customer currentCustomer = customerService.getByEmail(principal.getName());
      userDto.setName(currentCustomer.getName());
      userDto.setPhone(currentCustomer.getPhone());
      
      model.addAttribute("userDto", userDto);
      return "editUser";
   }
   
   @PostMapping("/edit")
   public String editUser(RedirectAttributes redirectAttributes,
                          @ModelAttribute UserDto userDto) {
      
      customerService.updateCustomer(userDto);
      
      return "redirect:/customer/edit";
   }
   
   @GetMapping("/forgotPassword")
   public String getForgottenPassword(Model model) {
      
      UserDto userDto = new UserDto();
      
      model.addAttribute("userDto", userDto);
      return "forgotPassword";
   }
   
}
