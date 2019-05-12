package com.team14.carservice.controllers;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.Event;
import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.service.common.CarService;
import com.team14.carservice.service.common.CustomerCarService;
import com.team14.carservice.service.common.EventService;
import com.team14.carservice.utils.GenericResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarRestController {
   
   private final CarService service;
   private final CustomerCarService customerCarService;
   private final EventService eventService;
   
   
   @Autowired
   public CarRestController(CarService service,  CustomerCarService customerCarService, EventService eventService) {
      this.service = service;
      this.customerCarService = customerCarService;
      this.eventService = eventService;
   }
   
   @GetMapping
   public List<Car> listAllCars() {
      return service.getAllCars();
   }
   
   @GetMapping("/{id}")
   public Car getById(@PathVariable Integer id) {
      return service.getById(id);
   }
   
   @PutMapping
   public Car update(@RequestBody @Valid Car car) {
      return service.edit(car);
   }
   
   @GetMapping("/model/{model}")
   public List<Car> getByModel(@PathVariable String model) {
      return service.getByModel(model);
   }
   
   @PostMapping("/create")
   public Car createCar(@RequestBody @Valid Car car) {
      return service.createCar(car);
   }
   
   @GetMapping("/owner/{carId}")
   public Customer getCustomerByCarId(@PathVariable int carId) {
      return customerCarService.getCustomerByCarId(carId).getCustomer();
   }
   
   @PostMapping("/prepare")
   public ResponseEntity<?> prepare(NewCarDto newCarDto) {
      
      try {
         customerCarService.assignCarToCustomerFromDto(newCarDto);
      } catch (Exception e) {
         return new ResponseEntity<>(new GenericResponse("Error assigning car to a customer"), HttpStatus.BAD_REQUEST);
      }
      
      return ResponseEntity.ok().body(new GenericResponse("Successfully assigned car to customer!"));
   }
   
   @PutMapping("/update/{carId}/{vin}/{licensePlate}")
   public void update(@PathVariable int carId, @PathVariable String vin, @PathVariable String licensePlate) {
      Car car1 = service.getById(carId);
      
      car1.setVin(vin);
      car1.setLicensePlate(licensePlate);
      
      service.edit(car1);
      
   }
   
   @GetMapping("/services/{id}")
   public List<Event> getServicesCar(@PathVariable int id) {
      return eventService.listAllEventsByCar(id);
   }
   
   @DeleteMapping("/delete/{id}")
   public void deleteCar(@PathVariable int id) {
      service.delete(service.getById(id));
      
   }
   
}
