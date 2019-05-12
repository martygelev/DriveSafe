package com.team14.carservice;

import com.team14.carservice.models.DetailedRepairService;
import com.team14.carservice.repository.DetailedRepairServiceRepository;
import com.team14.carservice.repository.RepairServiceRepository;
import com.team14.carservice.service.DetailedRepairServiceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import static com.team14.carservice.Helpers.repairServiceMaker;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DetailedRepairServicesTests {
   
   @Mock
   RepairServiceRepository repairServiceRepository;
   
   @Mock
   DetailedRepairServiceRepository detailedRepairServiceRepository;
   
   @InjectMocks
   DetailedRepairServiceServiceImpl detailedRepairServiceService;
   
   @Test
   public void createsNewDetailedRepairService(){
      
      DetailedRepairService detailedRepairService = new DetailedRepairService();
      detailedRepairService.setRepairService(repairServiceMaker());
      detailedRepairService.setRepairServiceId(1);
      detailedRepairService.setId(1);
      
      when(repairServiceRepository.findByIdAndDeletedFalse(1)).thenReturn(repairServiceMaker());
      
      detailedRepairServiceService.createNew(detailedRepairService);
      
      verify(detailedRepairServiceRepository, times(1)).save(any(DetailedRepairService.class));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createNewThrowsWhenBaseServiceDoesNotExist(){
      DetailedRepairService detailedRepairService = new DetailedRepairService();
      detailedRepairService.setRepairService(repairServiceMaker());
      detailedRepairService.setRepairServiceId(1);
      detailedRepairService.setId(1);
   
      when(repairServiceRepository.findByIdAndDeletedFalse(1)).thenReturn(null);
   
      detailedRepairServiceService.createNew(detailedRepairService);
      
      verify(detailedRepairServiceRepository, times(0)).save(any(DetailedRepairService.class));
   }
}
