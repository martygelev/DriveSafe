package com.team14.carservice.models;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
   
   private static final int EXPIRATION = 60 * 24;
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   
   private String token;
   
   @ManyToOne
   @JoinColumn(name = "customer_id")
   private Customer customer;
   
   private Date expiryDate;
   
   public PasswordResetToken() {
   }
   
   public PasswordResetToken(String token, Customer customer) {
      this.token = token;
      this.customer = customer;
      this.expiryDate = calculateExpiryDate();
   }
   
   public static int getEXPIRATION() {
      return EXPIRATION;
   }
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public String getToken() {
      return token;
   }
   
   public void setToken(String token) {
      this.token = token;
   }
   
   public Customer getCustomer() {
      return customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   public Date getExpiryDate() {
      return expiryDate;
   }
   
   public void setExpiryDate(Date expiryDate) {
      this.expiryDate = expiryDate;
   }
   
   private Date calculateExpiryDate() {
      final Calendar cal = Calendar.getInstance();
      cal.setTimeInMillis(new Date().getTime());
      cal.add(Calendar.MINUTE, PasswordResetToken.EXPIRATION);
      return new Date(cal.getTime().getTime());
   }
}