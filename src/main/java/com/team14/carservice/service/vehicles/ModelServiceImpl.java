package com.team14.carservice.service.vehicles;

import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.models.vehicles.Model;
import com.team14.carservice.repository.vehicles.ModelRepository;
import com.team14.carservice.service.common.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {
   
   private final ModelRepository repository;
   
   @Autowired
   public ModelServiceImpl(ModelRepository repository) {
      this.repository = repository;
   }
   
   @Override
   public List<Model> getAll(){
      return repository.findAll();
   }

   @Override
   public List<Model> getAllByManufacturer(Make make) {
      return repository.getAllByManufacturer(make);
   }

   @Override
   public List<Model> getAllByManufacturerName(String name) {
      return repository.getAllByManufacturer_Name(name);
   }
   
   @Override
   public Model getById(Integer id) {
      Model model = repository.getById(id);
      if (model == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      return model;
   }
}
