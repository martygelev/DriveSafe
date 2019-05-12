package com.team14.carservice.service.common;

import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.Car;

import java.util.List;

public interface CarService {
   
   List<Car> getAllCars();
   
   Car createCar(Car car);
   
   List<Car> getByModel(String model);

   Car getById(Integer id);
   
   Car edit(Car car);
   
   Car createCarFromDto(NewCarDto dto);

   void delete(Car car);
}
