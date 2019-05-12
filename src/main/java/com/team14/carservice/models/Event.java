package com.team14.carservice.models;

import com.team14.carservice.models.vehicles.CustomerCar;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "events")
@NaturalIdCache
@org.hibernate.annotations.Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class Event {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @Column(name = "date")
   private Date date;
   
   @ManyToOne
   @JoinColumn(name = "customer_car")
   private CustomerCar customerCar;
   
   @Column(name = "total_price")
   private Double totalPrice;
   
   @Column(name = "finalized")
   private Boolean finalized;
   
   @Column(name = "comment")
   private String comment;
   
   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   @JoinTable(name = "histories",
           joinColumns = @JoinColumn(name = "event_id", referencedColumnName = "id"),
           inverseJoinColumns = @JoinColumn(name = "detailed_service_id", referencedColumnName = "id"))
   private List<DetailedRepairService> detailedRepairServices;
   
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public Date getDate() {
      return date;
   }
   
   public void setDate(Date date) {
      this.date = date;
   }
   
   public CustomerCar getCustomerCar() {
      return customerCar;
   }
   
   public void setCustomerCar(CustomerCar customerCar) {
      this.customerCar = customerCar;
   }
   
   public Double getTotalPrice() {
      return totalPrice;
   }
   
   public void setTotalPrice(Double totalPrice) {
      this.totalPrice = totalPrice;
   }
   
   public Boolean getFinalized() {
      return finalized;
   }
   
   public void setFinalized(Boolean finalized) {
      this.finalized = finalized;
   }
   
   public String getComment() {
      return comment;
   }
   
   public void setComment(String comment) {
      this.comment = comment;
   }
   
   public List<DetailedRepairService> getDetailedRepairServices() {
      return detailedRepairServices;
   }
   
   public void setDetailedRepairServices(List<DetailedRepairService> detailedRepairServices) {
      this.detailedRepairServices = detailedRepairServices;
   }
   
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof Event)) {
         return false;
      }
      
      Event eventToCompare = (Event) o;
      
      return eventToCompare.id.equals(id) &&
              eventToCompare.date.equals(date) &&
              eventToCompare.customerCar.equals(customerCar) &&
              eventToCompare.totalPrice.equals(totalPrice) &&
              eventToCompare.finalized.equals(this.finalized);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + date.hashCode();
      result = 31 * result + customerCar.hashCode();
      result = 31 * result + totalPrice.hashCode();
      result = 31 * result + finalized.hashCode();
      return result;
   }
}
