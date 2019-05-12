package com.team14.carservice.utils;

import com.team14.carservice.models.Event;
import org.springframework.data.jpa.domain.Specification;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventSpecification {
   
   
   public static Specification<Event> customerCarIs(Integer customerCarId) {
      return (Specification<Event>) (root, query, criteriaBuilder) -> {
         if (customerCarId == -1) return null;
         return criteriaBuilder.equal(root.get("customerCar"), customerCarId);
      };
   }
   
   public static Specification<Event> eventStateIs(String wantedState) {
      return (Specification<Event>) (root, query, criteriaBuilder) -> {
         Boolean value = "Yes".equalsIgnoreCase(wantedState) ? Boolean.TRUE :
                 "No".equalsIgnoreCase(wantedState) ? Boolean.FALSE : null;
         if (value == null) return null;
         return criteriaBuilder.equal(root.get("finalized"), value);
      };
      
   }
   
   public static Specification<Event> customerIs(Integer customerId) {
      return (Specification<Event>) (root, query, criteriaBuilder)
              -> criteriaBuilder.equal(root.get("customerCar").get("customer").get("id"), customerId);
   }
   
   public static Specification<Event> dateIsBefore(String dateUntil) {
      
      Date date = checkValidDateInput(dateUntil);
      
      if (date == null) return null;
      else return (Specification<Event>) (root, query, criteriaBuilder)
              -> criteriaBuilder.lessThanOrEqualTo(root.get("date").as(Date.class), date);
   }
   
   public static Specification<Event> dateIsAfter(String dateAfter) {
      
      Date date = checkValidDateInput(dateAfter);
      
      if (date == null) return null;
      else return (Specification<Event>) (root, query, criteriaBuilder)
              -> criteriaBuilder.greaterThanOrEqualTo(root.get("date").as(Date.class), date);
   }
   
   private static Date checkValidDateInput(String inputDate) {
      if (inputDate == null
              || inputDate.equalsIgnoreCase("Any")
              || inputDate.equals("")) return null;
      
      Date date;
      
      try {
         DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         date = format.parse(inputDate);
      } catch (ParseException e) {
         return null;
      }
      return date;
   }
}

