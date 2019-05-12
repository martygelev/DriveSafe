package com.team14.carservice.models.dto;

public class EmailAddressDto {
   private int id;
   private String name, email, phone;
   
   public EmailAddressDto(int id, String name, String email, String phone) {
      this.id = id;
      this.name = name;
      this.email = email;
      this.phone = phone;
   }
   
   public int getId() {
      return id;
   }
   
   public void setId(int id) {
      this.id = id;
   }
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }
   
   public String getEmail() {
      return email;
   }
   
   public void setEmail(String email) {
      this.email = email;
   }
   
   public String getPhone() {
      return phone;
   }
   
   public void setPhone(String phone) {
      this.phone = phone;
   }
}
