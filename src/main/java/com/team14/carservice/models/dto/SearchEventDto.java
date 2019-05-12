package com.team14.carservice.models.dto;

public class SearchEventDto {
   
   private Integer customer, customerCar;
   private String finalized;
   
   private String from, to;
   
   public String getFrom() {
      return from;
   }
   
   public void setFrom(String from) {
      this.from = from;
   }
   
   public String getTo() {
      return to;
   }
   
   public void setTo(String to) {
      this.to = to;
   }
   
   public Integer getCustomer() {
      return customer;
   }
   
   public void setCustomer(Integer customer) {
      this.customer = customer;
   }
   
   public Integer getCustomerCar() {
      return customerCar;
   }
   
   public void setCustomerCar(Integer customerCar) {
      this.customerCar = customerCar;
   }
   
   public String getFinalized() {
      return finalized;
   }
   
   public void setFinalized(String finalized) {
      this.finalized = finalized;
   }
}
