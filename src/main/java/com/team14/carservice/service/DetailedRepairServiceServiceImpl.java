package com.team14.carservice.service;

import com.team14.carservice.models.DetailedRepairService;
import com.team14.carservice.models.RepairService;
import com.team14.carservice.repository.DetailedRepairServiceRepository;
import com.team14.carservice.repository.RepairServiceRepository;
import com.team14.carservice.service.common.DetailedRepairServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DetailedRepairServiceServiceImpl implements DetailedRepairServiceService {
   
   private final DetailedRepairServiceRepository detailedRepairServiceRepository;
   
   private final RepairServiceRepository repairServiceRepository;
   
   @Autowired
   public DetailedRepairServiceServiceImpl(DetailedRepairServiceRepository detailedRepairServiceRepository,
                                           RepairServiceRepository repairServiceRepository) {
      this.detailedRepairServiceRepository = detailedRepairServiceRepository;
      this.repairServiceRepository = repairServiceRepository;
   }
   
   @Override
   public void createNew(DetailedRepairService s) {
      RepairService baseService = repairServiceRepository.findByIdAndDeletedFalse(s.getRepairService().getId());
      
      if (baseService == null)
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Base repair service not found!");
      
      DetailedRepairService repairService = new DetailedRepairService();
      
      repairService.setPrice(s.getPrice());
      repairService.setServiceComment(s.getServiceComment());
      repairService.setRepairService(baseService);
      
      detailedRepairServiceRepository.save(repairService);
   }
}
