package com.team14.carservice.service.vehicles;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.vehicles.CustomerCarRepository;
import com.team14.carservice.service.common.CarService;
import com.team14.carservice.service.common.CustomerCarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class CustomerCarServiceImpl implements CustomerCarService {
   
   private final CarService carService;
   
   private final CustomerRepository customerRepository;
   
   private final CustomerCarRepository customerCarRepository;
   
   @Autowired
   public CustomerCarServiceImpl(CarService carService,
                                 CustomerRepository customerRepository,
                                 CustomerCarRepository customerCarRepository) {
      this.carService = carService;
      this.customerRepository = customerRepository;
      this.customerCarRepository = customerCarRepository;
   }
   
   @Override
   public List<CustomerCar> getAll() {
      
      List<CustomerCar> cars = customerCarRepository.findAll();
      
      if (cars.isEmpty()) {
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No CustomerCars currently added.");
      } else return cars;
   }
   
   public List<CustomerCar> getByCustomerId(Integer customerId) {
      
      if (!customerRepository.existsById(customerId))
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer with email %s not found", customerId));
      
      List<CustomerCar> cars = customerCarRepository.getAllByCustomer_Id(customerId);
      
      if (cars.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
              String.format("No cars for user wit id %s found", customerId));
      
      else return cars;
   }
   
   
   public CustomerCar assignCarToCustomerFromDto(NewCarDto newCarDto) {
      
      Customer customer = customerRepository
              .findById(newCarDto.getCustomerId())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer does not exist."));
      
      Car car;
      
      try {
         car = carService.createCarFromDto(newCarDto);
      } catch (Exception e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
         
      }
      
      
      if (customerCarRepository.existsByCar_IdAndCustomer_Id(car.getId(), customer.getId()))
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Car already assigned to customer.");
      
      CustomerCar customerCar = new CustomerCar();
      customerCar.setCarId(car.getId());
      customerCar.setCustomer(customer);
      
      customerCarRepository.save(customerCar);
      
      return customerCar;
   }
   
   @Override
   public List<CustomerCarDto> getAllByCustomerIdToDto(Integer id) {
      
      if (!customerRepository.existsById(id))
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Customer with email %s not found", id));
      
      return customerCarRepository.getAllByCustomer_IdToDto(id);
   }
   
   @Override
   public CustomerCar getCustomerByCarId(Integer carId) {
      return customerCarRepository.getByCar_Id(carId);
   }
   
}
