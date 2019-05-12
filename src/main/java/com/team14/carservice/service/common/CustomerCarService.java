package com.team14.carservice.service.common;

import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.CustomerCar;

import java.util.List;

public interface CustomerCarService {
   List<CustomerCar> getAll();
   
   List<CustomerCar> getByCustomerId(Integer customerId);
   
   CustomerCar assignCarToCustomerFromDto(NewCarDto dto);
   
   List<CustomerCarDto> getAllByCustomerIdToDto(Integer id);

   CustomerCar getCustomerByCarId(Integer carId);


}
