package com.team14.carservice.models.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserDto {
   
   @NotNull
   @NotEmpty(message = "Username must be at least 7 symbols")
   @Size(min = 7, message = "Username must be at least 7 symbols")
   private String email;
   
   @NotNull
   @NotEmpty(message = "Name must be at least 7 symbols")
   @Size(min = 5, message = "Name must be at least 5 symbols")
   private String name;
   
   private String phone;
   
   @NotNull
   @NotEmpty(message = "Password must be at least 7 symbols")
   @Size(min = 7, message = "Password must be at least 7 symbols")
   private String password;
   
   private String changePassword;
   
   public UserDto() {
   }

   public UserDto(@NotNull @NotEmpty(message = "Username must be at least 7 symbols") @Size(min = 7, message = "Username must be at least 7 symbols") String email, @NotNull @NotEmpty(message = "Name must be at least 7 symbols") @Size(min = 5, message = "Name must be at least 5 symbols") String name, String phone, @NotNull @NotEmpty(message = "Password must be at least 7 symbols") @Size(min = 7, message = "Password must be at least 7 symbols") String password, String changePassword) {
      this.email = email;
      this.name = name;
      this.phone = phone;
      this.password = password;
      this.changePassword = changePassword;
   }

   public String getEmail() {
      return email;
   }
   
   public void setEmail(String email) {
      this.email = email;
   }
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }
   
   public String getPhone() {
      return phone;
   }
   
   public void setPhone(String phone) {
      this.phone = phone;
   }
   
   public String getPassword() {
      return password;
   }
   
   public void setPassword(String password) {
      this.password = password;
   }
   
   public String getChangePassword() {
      return changePassword;
   }
   
   public void setChangePassword(String changePassword) {
      this.changePassword = changePassword;
   }
}
