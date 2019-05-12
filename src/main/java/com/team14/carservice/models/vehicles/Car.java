package com.team14.carservice.models.vehicles;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "cars")
public class Car {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @ManyToOne
   @JoinColumn(name = "model_id")
   private Model model;
   
   @NotNull(message = "vin should not be null.")
   @Size(min = 10, max = 20, message = "vin must be exactly 17 symbols.")
   @Column(name = "vin", unique = true)
   private String vin;
   
   @NotNull(message = "License Plate should not be null.")
   @Column(name = "license_plate", unique = true)
   private String licensePlate;
   
   @Column(name = "deleted")
   private Boolean deleted;
   
   public Car() {
   }
   
   public Car(Model model, @NotNull(message = "vin should not be null.") @Size(min = 10, max = 20, message = "vin must be exactly 17 symbols.") String vin, @NotNull(message = "License Plate should not be null.") String licensePlate, Boolean deleted) {
      this.model = model;
      this.vin = vin;
      this.licensePlate = licensePlate;
      this.deleted = deleted;
   }
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public Model getModel() {
      return model;
   }
   
   public void setModel(Model model) {
      this.model = model;
   }
   
   public String getVin() {
      return vin;
   }
   
   public void setVin(String vin) {
      this.vin = vin;
   }
   
   public String getLicensePlate() {
      return licensePlate;
   }
   
   public void setLicensePlate(String licensePlate) {
      this.licensePlate = licensePlate;
   }
   
   public Boolean getDeleted() {
      return deleted;
   }
   
   public void setDeleted(Boolean deleted) {
      this.deleted = deleted;
   }
   
   public String getModelManufacturerName(){
      return this.model.getManufacturerName();
   }
   
   public String getModelName(){
      return this.model.getName();
   }
   
   public String getModelYearOnly(){
      return this.model.getOnlyYear();
   }
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof Car)) {
         return false;
      }
      
      Car carToCompare = (Car) o;
      
      return carToCompare.id.equals(id) &&
              carToCompare.deleted.equals(deleted) &&
              carToCompare.licensePlate.equals(licensePlate) &&
              carToCompare.vin.equals(vin) &&
              carToCompare.model.equals(model);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + licensePlate.hashCode();
      result = 31 * result + vin.hashCode();
      result = 31 * result + deleted.hashCode();
      result = 31 * result + model.hashCode();
      return result;
   }
}
