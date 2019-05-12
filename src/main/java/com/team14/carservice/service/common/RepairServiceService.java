package com.team14.carservice.service.common;

import com.team14.carservice.models.RepairService;

import java.util.List;

public interface RepairServiceService {
   List<RepairService> listAllRepairServices();
   
   RepairService createNewRepairService(RepairService repairService);
   
   RepairService editRepairService(RepairService repairService);
   
   RepairService getRepairServiceById(Integer id);
   
   void markServiceDeleted(Integer id);
}
