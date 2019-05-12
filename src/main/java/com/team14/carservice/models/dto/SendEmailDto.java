package com.team14.carservice.models.dto;

public class SendEmailDto {
   private String to, subject, text;
   
   public String getTo() {
      return to;
   }
   
   public void setTo(String to) {
      this.to = to;
   }
   
   public String getSubject() {
      return subject;
   }
   
   public void setSubject(String subject) {
      this.subject = subject;
   }
   
   public String getText() {
      return text;
   }
   
   public void setText(String text) {
      this.text = text;
   }
}
