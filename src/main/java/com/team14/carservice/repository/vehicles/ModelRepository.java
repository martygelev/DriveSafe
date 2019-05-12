package com.team14.carservice.repository.vehicles;

import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends JpaRepository<Model, Integer> {

    Model getById(Integer id);
    List<Model> getAllByManufacturer(Make make);
    List<Model> getAllByManufacturer_Name(String makeName);
}
