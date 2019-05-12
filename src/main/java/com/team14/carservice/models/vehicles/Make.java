package com.team14.carservice.models.vehicles;

import javax.persistence.*;

@Entity
@Table(name = "makes")
public class Make {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;
   
   @Column(name = "name")
   private String name;
   
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
   
   @Override
   public boolean equals(Object o) {
      if (o == this) return true;
      
      if (!(o instanceof Make)) {
         return false;
      }
      
      Make makeToCompare = (Make) o;
      
      return makeToCompare.id.equals(id) &&
              makeToCompare.name.equals(name);
   }
   
   @Override
   public int hashCode() {
      int result = 17;
      result = 31 * result + id;
      result = 31 * result + name.hashCode();
      return result;
   }
}
