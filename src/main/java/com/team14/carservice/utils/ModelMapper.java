package com.team14.carservice.utils;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.CustomerDto;
import com.team14.carservice.models.vehicles.CustomerCar;

public class ModelMapper {
   
   public static CustomerDto toDto(Customer customer){
      CustomerDto dto = new CustomerDto();
      dto.setId(customer.getId());
      dto.setName(customer.getName());
      return dto;
   }
   
   public static CustomerCarDto toDto(CustomerCar customerCar){
      CustomerCarDto dto = new CustomerCarDto();
      dto.setId(customerCar.getId());
      dto.setLicensePlate(customerCar.getCar().getLicensePlate());
      dto.setModel(customerCar.getCarModelName());
      dto.setMake(customerCar.getCarMakeName());
      dto.setYear(customerCar.getCarYear());
      return dto;
   }
}
