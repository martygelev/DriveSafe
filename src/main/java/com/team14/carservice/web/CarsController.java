package com.team14.carservice.web;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;
import com.team14.carservice.service.common.CarService;
import com.team14.carservice.service.common.CustomerCarService;
import com.team14.carservice.service.common.MakeService;
import com.team14.carservice.service.common.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/cars")
public class CarsController {
   
   private final CarService carService;
   private final MakeService makeService;
   private final ModelService modelService;
   private final CustomerCarService customerCarService;
   
   @Autowired
   public CarsController(CarService carService, MakeService makeService, ModelService modelService, CustomerCarService customerCarService) {
      this.carService = carService;
      this.makeService = makeService;
      this.modelService = modelService;
      this.customerCarService = customerCarService;
   }
   
   @GetMapping
   public ModelAndView getAllCars() {
      ModelAndView model = new ModelAndView("admin/allCars");
      
      List<Car> carsList = carService.getAllCars();
      model.addObject("cars", carsList);
      
      return model;
   }
   
   @RequestMapping(value = ("/newCar"), method = RequestMethod.GET)
   public ModelAndView createCar() {
      ModelAndView model = new ModelAndView("admin/newCar");
      Car car = new Car();
      Make make = new Make();
      model.addObject("car", car);
      model.addObject("make", make);
      
      
      List<Make> makes = makeService.getAll();
      model.addObject("makes", makes);
      
      List<Model> models = modelService.getAllByManufacturer(make);
      model.addObject("models", models);
      
      model.setViewName("admin/newCar");
      
      return model;
   }
   
   @PostMapping("/newCar")
   public String createCar(@Valid Car car, BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
      
      
      Model modelCar = car.getModel();
      
      car.setModel(modelCar);
      car.setLicensePlate(car.getLicensePlate());
      car.setVin(car.getVin());
      car.setDeleted(false);
      
      carService.createCar(car);
      
      return "redirect:/customers";
   }
   
   
   @GetMapping(value = "/{id}")
   public ModelAndView getCarById(@PathVariable(value = "id") Integer id) {
      
      ModelAndView model = new ModelAndView("admin/carInfo");
      Car car = carService.getById(id);
      Customer customer = customerCarService.getCustomerByCarId(id).getCustomer();
      
      CustomerCar custCar = customerCarService.getCustomerByCarId(id);
      
      model.addObject("car", car);
      model.addObject("customer", customer);
      return model;
      
   }
   
   @GetMapping("/edit/{id}")
   public String getEditIndex(@PathVariable int id, org.springframework.ui.Model model) {
      Car currentCar = carService.getById(id);
      model.addAttribute("currentCar", currentCar);
      return "carInfo";
   }
   
   @PostMapping("/edit/{id}")
   public String editUser(@PathVariable int id, RedirectAttributes redirectAttributes,
                          @ModelAttribute Car car) {
      
      carService.edit(carService.getById(id));
      
      return "redirect:/cars/carInfo" + id;
   }

}
