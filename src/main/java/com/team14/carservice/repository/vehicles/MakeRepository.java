package com.team14.carservice.repository.vehicles;

import com.team14.carservice.models.vehicles.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeRepository extends JpaRepository<Make, Integer> {

}
