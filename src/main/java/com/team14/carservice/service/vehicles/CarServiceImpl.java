package com.team14.carservice.service.vehicles;

import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.repository.vehicles.CarRepository;
import com.team14.carservice.service.common.CarService;
import com.team14.carservice.service.common.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CarServiceImpl implements CarService {
   
   private final CarRepository repository;
   
   private final ModelService modelService;
   
   @Autowired
   public CarServiceImpl(CarRepository repository,
                         ModelService modelService) {
      this.repository = repository;
      this.modelService = modelService;
   }
   
   public List<Car> getAllCars() {
      return repository.getAllByDeletedFalse()
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No cars currently added."));
   }
   
   @Override
   public Car edit(Car car) {
      if (car == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Car is empty");
      return repository.save(car);
   }
   
   @Override
   public void delete(Car car) {
      repository.delete(car);
   }
   
   public Car getById(Integer id) {
      return repository.getByIdAndDeletedFalse(id)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No cars with id %d", id)));
   }
   
   public List<Car> getByModel(String model) {
      return repository.getAllByModel_NameAndDeletedFalse(model)
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No cars with that model."));
   }
   
   public Car createCar(Car car) {
      
      if (car == null || repository.getByLicensePlateAndDeletedFalse(car.getLicensePlate()).isPresent()) {
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Car already exists");
      }
      
      return repository.save(car);
   }
   
   public Car createCarFromDto(NewCarDto dto) {
      
      Car car = new Car();
      car.setLicensePlate(dto.getLicensePlate());
      car.setDeleted(false);
      car.setVin(dto.getVin());
      
      try {
         car.setModel(modelService.getById(dto.getModelId()));
         return repository.save(car);
      } catch (Exception e) {
         System.out.println(e.getMessage());
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }
   }
}
