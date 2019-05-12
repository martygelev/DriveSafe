package com.team14.carservice.repository;

import com.team14.carservice.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
   
   @Query(value = "SELECT * FROM events e\n" +
           "JOIN customer_cars c ON e.customer_car = c.id\n" +
           "JOIN customers s ON c.customer_id = s.id\n" +
           "WHERE s.id = ?1", nativeQuery = true)
   Optional<List<Event>> getAllByCustomer(Integer customerId);
   
   @Query(value = "SELECT * FROM events e\n" +
           "JOIN customer_cars c ON e.customer_car = c.id\n" +
           "JOIN cars s ON c.car_id = s.id\n" +
           "WHERE s.id = ?", nativeQuery = true)
   Optional<List<Event>> getAllByCar(Integer carId);
   
   @Query(value = "SELECT COUNT(*) FROM events e\n" +
           "JOIN customer_cars c\n" +
           "on e.customer_car = c.id\n" +
           "WHERE c.customer_id = ?1", nativeQuery = true)
   Integer countAllVisits(int id);
   
   @Query(value = "SELECT COUNT(*) FROM events e\n" +
           "JOIN customer_cars c\n" +
           "on e.customer_car = c.id\n" +
           "WHERE c.customer_id = ?1\n" +
           "AND e.finalized = 0", nativeQuery = true)
   Integer countNotFinalizedEvents(int id);
}
