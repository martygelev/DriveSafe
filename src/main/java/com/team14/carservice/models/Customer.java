package com.team14.carservice.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.team14.carservice.models.vehicles.CustomerCar;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @Size(min = 7, max = 50, message = "Email cannot be less that 7 symbols")
   @Email
   @Column(name = "email")
   private String email;
   
   @Size(min = 7, max = 15, message = "Phone number cannot be less that 7 and more than 15 symbols")
   @Column(name = "phone")
   private String phone;
   
   @Size(min = 5, max = 65, message = "Name cannot be less that 5 and more than 65 symbols")
   @Column(name = "name")
   private String name;
   
   @Column(name = "deleted")
   private boolean deleted;
   
   @ManyToMany(fetch = FetchType.EAGER)
   @JoinTable(name = "customer_cars",
           joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "car_id", referencedColumnName = "id"))
   private List<CustomerCar> carList;
   
   public Customer() {
      this.deleted = false;
   }
   
   public Customer(String email, String phone, String name) {
      this.email = email;
      this.phone = phone;
      this.name = name;
      this.deleted = false;
   }
   
   public boolean isDeleted() {
      return deleted;
   }
   
   public void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }
   
   @JsonIgnore
   public List<CustomerCar> getCarList() {
      return carList;
   }
   
   public void setCarList(List<CustomerCar> carList) {
      this.carList = carList;
   }
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
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
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }
   
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof Customer)) {
         return false;
      }
      
      Customer customerToCompare = (Customer) o;
      
      return customerToCompare.id.equals(id) &&
              customerToCompare.email.equals(email) &&
              customerToCompare.phone.equals(phone) &&
              customerToCompare.name.equals(name);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + phone.hashCode();
      result = 31 * result + email.hashCode();
      result = 31 * result + name.hashCode();
      return result;
   }
}
