package com.team14.carservice.repository.vehicles;

import com.team14.carservice.models.dto.CustomerCarDto;
import com.team14.carservice.models.vehicles.CustomerCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCarRepository extends JpaRepository<CustomerCar, Integer> {

    List<CustomerCar> getAllByCustomer_Id(Integer id);

    Boolean existsByCar_IdAndCustomer_Id(Integer carId, Integer customerId);

    @Query(value = "SELECT new com.team14.carservice.models.dto.CustomerCarDto" +
            "(id, car) from CustomerCar c where c.customer.id = :#{#id}")
    List<CustomerCarDto> getAllByCustomer_IdToDto(@Param("id") Integer id);

    @Query(value = "SELECT * from customer_cars c order by c.id desc limit 1", nativeQuery = true)

    CustomerCar getFirstById(Integer id);

    CustomerCar getByCar_Id (Integer id);
}
