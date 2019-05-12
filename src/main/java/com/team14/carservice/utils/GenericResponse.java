package com.team14.carservice.utils;

public class GenericResponse {
   
   private String message;
   
   public GenericResponse(final String message) {
      this.message = message;
   }
   
   
   public String getMessage() {
      return message;
   }
   
   public void setMessage(final String message) {
      this.message = message;
   }
   
   
}