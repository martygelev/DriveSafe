package com.team14.carservice.models;

import javax.persistence.*;

@Entity
@Table(name = "detailed_services")
public class DetailedRepairService {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Integer id;
   
   @ManyToOne
   @JoinColumn(name = "service_id")
   private RepairService repairService;
   
   @Column(name = "price")
   private Double price;
   
   @Column(name = "comment")
   private String serviceComment;
   
   public DetailedRepairService() {
   }
   
   public DetailedRepairService(RepairService repairService, Double price, String serviceComment) {
      this.repairService = repairService;
      this.price = price;
      this.serviceComment = serviceComment;
   }
   
   public void setRepairServiceId(Integer id) {
      this.repairService.setId(id);
   }
   
   public Integer getId() {
      return id;
   }
   
   public void setId(Integer id) {
      this.id = id;
   }
   
   public RepairService getRepairService() {
      return repairService;
   }
   
   public void setRepairService(RepairService repairService) {
      this.repairService = repairService;
   }
   
   public Double getPrice() {
      return price;
   }
   
   public void setPrice(Double price) {
      this.price = price;
   }
   
   public String getServiceComment() {
      return serviceComment;
   }
   
   public void setServiceComment(String serviceComment) {
      this.serviceComment = serviceComment;
   }
}
