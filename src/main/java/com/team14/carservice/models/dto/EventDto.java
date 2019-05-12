package com.team14.carservice.models.dto;

import java.util.Date;

public class EventDto {
   
   private int id;
   private String customer;
   private double price;
   private String date;
   private boolean finalized;
   private String comment;
   
   public EventDto() {
   }
   
   
   public EventDto(int id, String customer, double price, Date date, String comment) {
      this.id = id;
      this.customer = customer;
      this.price = price;
      this.date = date.toString().substring(0, 9);
      this.comment = comment;
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public String getCustomer() {
      return customer;
   }
   
   public void setCustomer(String customer) {
      this.customer = customer;
   }
   
   public double getPrice() {
      return price;
   }
   
   public void setPrice(double price) {
      this.price = price;
   }
   
   public boolean isFinalized() {
      return finalized;
   }
   
   public void setFinalized(boolean finalized) {
      this.finalized = finalized;
   }
   
   public String getDate() {
      return date;
   }
   
   public void setDate(String date) {
      this.date = date;
   }
   
   public String getComment() {
      return comment;
   }
   
   public void setComment(String comment) {
      this.comment = comment;
   }
}
