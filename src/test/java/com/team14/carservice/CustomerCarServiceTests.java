package com.team14.carservice;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.vehicles.CarRepository;
import com.team14.carservice.repository.vehicles.CustomerCarRepository;
import com.team14.carservice.service.common.CarService;
import com.team14.carservice.service.common.ModelService;
import com.team14.carservice.service.vehicles.CustomerCarServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static com.team14.carservice.Helpers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerCarServiceTests {
   
   @Mock
   CustomerCarRepository customerCarRepository;
   
   @Mock
   CustomerRepository customerRepository;
   
   @Mock
   CarRepository carRepository;
   
   @Mock
   CarService carService;
   
   @Mock
   ModelService modelService;
   
   @InjectMocks
   CustomerCarServiceImpl customerCarServiceImpl;
   
   @Test(expected = ResponseStatusException.class)
   public void getAllThrowsWhenEmpty() {
      
      when(customerCarRepository.findAll()).thenReturn(Collections.emptyList());
      
      customerCarServiceImpl.getAll();
      
   }
   
   @Test
   public void getAllReturnsAllWhenNotEmpty() {
      
      when(customerCarRepository.findAll()).thenReturn(Collections.singletonList(customerCarMaker()));
      
      Assert.assertEquals(customerCarServiceImpl.getAll().size(), 1);
      
      Mockito.verify(customerCarRepository, Mockito.times(1)).findAll();
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void assignCarToCustomerThrowsWhenCustomerDoesNotExist() {
      
      customerCarServiceImpl.assignCarToCustomerFromDto(newCarDtoMaker());
      
   }
   
   @Test
   public void assignCarToCustomerWhenCustomerExist() {
      Customer customer = customerMaker();
      Car car = carMaker();
      NewCarDto dto = newCarDtoMaker();
      
      when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.of(customer));
      
      when(carService.createCarFromDto(dto)).thenReturn(car);
      
      when(customerCarRepository.existsByCar_IdAndCustomer_Id(car.getId(), customer.getId())).thenReturn(false);
      
      customerCarServiceImpl.assignCarToCustomerFromDto(dto);
      
      Mockito.verify(customerCarRepository, Mockito.times(1)).save(any(CustomerCar.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void assignCarToCustomerThrowsWhenAlreadyAssigned() {
      customerCarServiceImpl.assignCarToCustomerFromDto(newCarDtoMaker());
      
   }
   
   
   @Test
   public void assignCarToCustomerExecutesWhenNotAlreadyAssigned() {
      Customer customer = customerMaker();
      Car car = carMaker();
      NewCarDto dto = newCarDtoMaker();
      
      when(customerRepository.findById(dto.getCustomerId())).thenReturn(Optional.of(customer));
      
      when(carService.createCarFromDto(any(NewCarDto.class))).thenReturn(car);
      
      customerCarServiceImpl.assignCarToCustomerFromDto(newCarDtoMaker());
      
      Mockito.verify(customerCarRepository, Mockito.times(1)).save(any(CustomerCar.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void assignCarToCustomerThrowsWhenCarDoesNotExist() {
      customerCarServiceImpl.assignCarToCustomerFromDto(newCarDtoMaker());
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getByCustomerIdThrowsWhenCustomerDoesNotExist() {
      
      Customer customer = customerMaker();
      
      when(customerRepository.existsById(customer.getId())).thenReturn(false);
      
      customerCarServiceImpl.getByCustomerId(customer.getId());
      
   }
   
   
   @Test(expected = ResponseStatusException.class)
   public void getByCustomerIdThrowsWhenEmpty() {
      
      Customer customer = customerMaker();
      
      when(customerRepository.existsById(customer.getId())).thenReturn(true);
      
      customerCarServiceImpl.getByCustomerId(customer.getId());
      
      
   }
   
   @Test
   public void getByCustomerIdExecutesWhenCustomerExists() {
      
      Customer customer = customerMaker();
      
      when(customerRepository.existsById(customer.getId())).thenReturn(true);
      
      when(customerCarRepository.getAllByCustomer_Id(customer.getId())).thenReturn(Collections.singletonList(customerCarMaker()));
      
      customerCarServiceImpl.getByCustomerId(customer.getId());
      
      Mockito.verify(customerCarRepository, Mockito.times(1)).getAllByCustomer_Id(customer.getId());
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getByCustomerIdToDtoThrowsWhenCustomerDoesNotExist() {
      
      Customer customer = customerMaker();
      
      when(customerRepository.existsById(customer.getId())).thenReturn(false);
      
      customerCarServiceImpl.getAllByCustomerIdToDto(customer.getId());
      
   }
   
   @Test
   public void getByCustomerIdToDtoExecutesWhenCustomerExists() {
      
      Customer customer = customerMaker();
      
      when(customerRepository.existsById(customer.getId())).thenReturn(true);
      
      customerCarServiceImpl.getAllByCustomerIdToDto(customer.getId());
      
      Mockito.verify(customerCarRepository, Mockito.times(1)).getAllByCustomer_IdToDto(customer.getId());
      
   }
   
}
