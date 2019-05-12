package com.team14.carservice.service;

import com.team14.carservice.models.RepairService;
import com.team14.carservice.repository.RepairServiceRepository;
import com.team14.carservice.service.common.RepairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class RepairServiceServiceImpl implements RepairServiceService {
   
   private final RepairServiceRepository repository;
   
   @Autowired
   public RepairServiceServiceImpl(RepairServiceRepository repository) {
      this.repository = repository;
   }
   
   @Override
   public List<RepairService> listAllRepairServices() {
      
      return Optional.ofNullable(repository.findAllByDeletedFalseOrderByName())
              .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                      "No services currently added."));
      
   }
   
   public RepairService getRepairServiceById(Integer id) {
      
      return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No service with id %d.", id)));
      
   }
   
   @Override
   public RepairService createNewRepairService(RepairService repairService) {
      
      if (recordExists(repairService.getName()))
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Service already exists.");
      
      return repository.save(repairService);
   }
   
   @Override
   public RepairService editRepairService(RepairService repairService) {
      
      if (!recordExists(repairService.getId()))
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Service does not exist.");
      
      return repository.save(repairService);
   }
   
   @Override
   public void markServiceDeleted(Integer id) {
      RepairService service = repository.findByIdAndDeletedFalse(id);
      service.setDeleted(true);
      repository.save(service);
   }
   
   private Boolean recordExists(Integer id) {
      return repository.existsByIdAndDeletedFalse(id);
   }
   
   private Boolean recordExists(String name) {
      return repository.existsByNameAndDeletedFalse(name);
   }
}
