package com.team14.carservice.repository.vehicles;

import com.team14.carservice.models.vehicles.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
   
   Optional<Car> getByLicensePlateAndDeletedFalse(String plate);
   
   Optional<List<Car>> getAllByDeletedFalse();
   
   Optional<Car> getByIdAndDeletedFalse(Integer id);
   
   Optional<List<Car>> getAllByModel_NameAndDeletedFalse(String  model);


   
}
