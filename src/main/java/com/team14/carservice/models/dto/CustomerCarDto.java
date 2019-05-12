package com.team14.carservice.models.dto;

import com.team14.carservice.models.vehicles.Car;

public class CustomerCarDto {
   private int id;
   private String make;
   private String model;
   private String  year;
   private String licensePlate;
   
   public CustomerCarDto() {
   }
   
   public CustomerCarDto(int id, Car car) {
      this.id = id;
      this.make = car.getModelManufacturerName();
      this.model = car.getModelName();
      this.year = car.getModelYearOnly();
      this.licensePlate = car.getLicensePlate();
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public String getMake() {
      return make;
   }
   
   public void setMake(String make) {
      this.make = make;
   }
   
   public String getModel() {
      return model;
   }
   
   public void setModel(String model) {
      this.model = model;
   }
   
   public String getYear() {
      return year;
   }
   
   public void setYear(String year) {
      this.year = year;
   }
   
   public String getLicensePlate() {
      return licensePlate;
   }
   
   public void setLicensePlate(String licensePlate) {
      this.licensePlate = licensePlate;
   }
}
