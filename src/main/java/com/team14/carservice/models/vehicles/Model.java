package com.team14.carservice.models.vehicles;

import javax.persistence.*;

@Entity
@Table(name = "models")
public class Model {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @Column(name = "model")
   private String name;
   
   @Column(name = "year")
   private String year;
   
   @ManyToOne(cascade=CascadeType.ALL)
   @JoinColumn(name = "make_id")
   private Make manufacturer;
   
   public Integer getId() {
      return id;
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
   
   public String getYear() {
      return year;
   }
   
   public void setYear(String year) {
      this.year = year;
   }
   
   public Make getManufacturer() {
      return manufacturer;
   }
   
   public String getManufacturerName(){
      return this.manufacturer.getName();
   }
   
   public void setManufacturer(Make manufacturer) {
      this.manufacturer = manufacturer;
   }
   
   public String getOnlyYear(){
      return this.year.substring(0,4);
   }
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof Model)) {
         return false;
      }
      
      Model modelToCompare = (Model) o;
      
      return modelToCompare.id.equals(id) &&
              modelToCompare.name.equals(name) &&
              modelToCompare.year.equals(year) &&
              modelToCompare.manufacturer.equals(this.manufacturer);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + name.hashCode();
      result = 31 * result + year.hashCode();
      result = 31 * result + manufacturer.hashCode();
      return result;
   }
   
}