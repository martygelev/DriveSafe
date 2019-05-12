package com.team14.carservice.models;

import javax.persistence.*;

@Entity
@Table(name = "services")
public class RepairService {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @Column(name = "name")
   private String name;
   
   @Column(name = "price")
   private Double price;
   
   @Column(name = "deleted")
   private Boolean deleted;
   
   public Integer getId() {
      return id;
   }
   
   public RepairService() {
      this.deleted = false;
   }
   
   public RepairService(String name, Double price) {
      this.name = name;
      this.price = price;
      this.deleted = false;
   }
   
   public Boolean getDeleted() {
      return deleted;
   }
   
   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public String getName() {
      return name;
   }
   
   public void setName(String name) {
      this.name = name;
   }
   
   public Double getPrice() {
      return price;
   }
   
   public void setPrice(Double price) {
      this.price = price;
   }
   
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof RepairService)) {
         return false;
      }
      
      RepairService serviceToCompare = (RepairService) o;
      
      return serviceToCompare.id.equals(id) &&
              serviceToCompare.name.equals(name) &&
              serviceToCompare.price.equals(price);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + name.hashCode();
      result = 31 * result + price.hashCode();
      return result;
   }
}
