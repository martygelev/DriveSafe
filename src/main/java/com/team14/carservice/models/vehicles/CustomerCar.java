package com.team14.carservice.models.vehicles;

import com.team14.carservice.models.Customer;

import javax.persistence.*;

@Entity
@Table(name = "customer_cars")
public class CustomerCar {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @ManyToOne
   @JoinColumn(name = "car_id")
   private Car car;

   @ManyToOne
   @JoinColumn(name = "customer_id")
   private Customer customer;
   
   public CustomerCar() {
      car = new Car();
   }
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public void setCarId(Integer id){
      this.car.setId(id);
   }
   
   public Car getCar() {
      return car;
   }
   
   public void setCar(Car car) {
      this.car = car;
   }
   
   public Customer getCustomer() {
      return customer;
   }
   
   public void setCustomer(Customer customer) {
      this.customer = customer;
   }
   
   public String getCustomerEmail(){
      return customer.getEmail();
   }
   
   public String getCarModelName(){
      return car.getModelName();
   }
   
   public String getCarMakeName(){
      return car.getModelManufacturerName();
   }
   
   public String getCarYear(){
      return car.getModelYearOnly();
   }


   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof CustomerCar)) {
         return false;
      }
      
      CustomerCar carToCompare = (CustomerCar) o;
      
      return carToCompare.id.equals(id) &&
              carToCompare.customer.equals(customer) &&
              carToCompare.car.equals(car);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + customer.hashCode();
      result = 31 * result + car.hashCode();
      return result;
   }
   
   @Override
   public String toString() {
      return String.format("%s, %s, %s, %s",
              this.car.getModelManufacturerName(),
              this.car.getModelName(),
              this.car.getModelYearOnly(),
              this.car.getLicensePlate());
   }
}
