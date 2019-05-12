package com.team14.carservice;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.Event;
import com.team14.carservice.models.PasswordResetToken;
import com.team14.carservice.models.RepairService;
import com.team14.carservice.models.dto.NewCarDto;
import com.team14.carservice.models.dto.UserDto;
import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Helpers {
   
   public static NewCarDto newCarDtoMaker(){
      NewCarDto newCarDto = new NewCarDto();
      newCarDto.setCustomerId(1);
      newCarDto.setModelId(1);
      newCarDto.setLicensePlate("1asda323");
      newCarDto.setVin("2324323232432");
      return newCarDto;
   }
   
   public static Make makeMaker(){
      Make make = new Make();
      make.setId(1);
      make.setName("testMake");
      
      return make;
   }
   
   public static Model modelMaker(){
      Model model = new Model();
      model.setId(1);
      model.setManufacturer(makeMaker());
      model.setName("testModel");
      model.setYear("1990");
      
      return model;
   }
   
   public static Car carMaker() {
      
      Car car = new Car();
      car.setId(1);
      car.setDeleted(false);
      car.setLicensePlate("12345678");
      car.setVin("11111111111111111");
      car.setModel(modelMaker());
      
      return car;
   }
   
   public static PasswordResetToken tokenMaker(){
      PasswordResetToken token = new PasswordResetToken();
   
      Calendar today = Calendar.getInstance();
      today.clear(Calendar.HOUR); today.clear(Calendar.MINUTE); today.clear(Calendar.SECOND);
      Date todayDate = today.getTime();
      
      token.setExpiryDate(todayDate);
      token.setCustomer(customerMaker());
      token.setId(1);
      token.setToken("testToken");
      return token;
   }
   
   public static Customer customerMaker() {
      Customer customer = new Customer();
      customer.setName("test");
      customer.setPhone("123");
      customer.setEmail("test@test.com");
      return customer;
   }
   
   public static CustomerCar customerCarMaker(){
      CustomerCar customerCar = new CustomerCar();
      customerCar.setCustomer(customerMaker());
      customerCar.setCar(carMaker());
      
      return customerCar;
   }
   
   public static RepairService repairServiceMaker(){
      RepairService repairService = new RepairService();
      repairService.setId(1);
      repairService.setName("testRepairService");
      repairService.setPrice(99.95);
      
      return repairService;
   }
   
   public static Event eventMaker(){
      
      Event event = new Event();
      event.setCustomerCar(customerCarMaker());
      event.setDate(new Date());
      event.setFinalized(false);
      event.setId(1);
      event.setTotalPrice(123.45);
      event.setDetailedRepairServices(new ArrayList<>());
      
      return event;
   }
   
   public static UserDto userDtoMaker(){
   
      UserDto dto = new UserDto();
      dto.setName("test");
      dto.setPassword("testtesttest");
      dto.setPhone("123456");
      dto.setEmail("test@test.test");
      return dto;
   }
   
   public static User userMaker(){
      UserDto userDto = userDtoMaker();
      return new User(userDto.getEmail(), "pass", new ArrayList<>());
   }
   
   
}
