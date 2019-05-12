package com.team14.carservice.service.vehicles;

import com.team14.carservice.models.vehicles.Make;
import com.team14.carservice.repository.vehicles.MakeRepository;
import com.team14.carservice.service.common.MakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MakeServiceImpl implements MakeService {
   
   private final MakeRepository repository;
   
   @Autowired
   public MakeServiceImpl(MakeRepository repository) {
      this.repository = repository;
   }
   
   @Override
   public List<Make> getAll() {
      return repository.findAll();
   }
}
