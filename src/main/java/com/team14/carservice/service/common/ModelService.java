package com.team14.carservice.service.common;

import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;

import java.util.List;

public interface ModelService {
   List<Model> getAll();
   Model getById(Integer id);
   List<Model> getAllByManufacturer(Make make);
   List<Model> getAllByManufacturerName(String name);


}
