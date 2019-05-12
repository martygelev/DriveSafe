package com.team14.carservice.models.dto;

public class NewCarDto {
   
   private int customerId, modelId;
   private String vin, licensePlate;
   
   public int getCustomerId() {
      return customerId;
   }
   
   public void setCustomerId(int customerId) {
      this.customerId = customerId;
   }
   
   public int getModelId() {
      return modelId;
   }
   
   public void setModelId(int modelId) {
      this.modelId = modelId;
   }
   
   public String getVin() {
      return vin;
   }
   
   public void setVin(String vin) {
      this.vin = vin;
   }
   
   public String getLicensePlate() {
      return licensePlate;
   }
   
   public void setLicensePlate(String licensePlate) {
      this.licensePlate = licensePlate;
   }
}
