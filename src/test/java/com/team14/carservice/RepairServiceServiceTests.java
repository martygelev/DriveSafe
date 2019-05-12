package com.team14.carservice;

import com.team14.carservice.models.RepairService;
import com.team14.carservice.repository.EventRepository;
import com.team14.carservice.repository.RepairServiceRepository;
import com.team14.carservice.service.RepairServiceServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static com.team14.carservice.Helpers.repairServiceMaker;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RepairServiceServiceTests {
   
   @Mock
   RepairServiceRepository mockRepairServiceRepository;
   
   @Mock
   EventRepository mockEventRepository;
   
   @InjectMocks
   RepairServiceServiceImpl mockRepairServiceService;
   
   @Test(expected = ResponseStatusException.class)
   public void listAllThrowsWhenEmpty() {
      
      when(mockRepairServiceRepository.findAllByDeletedFalseOrderByName()).thenReturn(null);
      
      mockRepairServiceService.listAllRepairServices();
      
   }
   
   @Test
   public void listAllReturnsAllWhenNotEmpty() {
      
      when(mockRepairServiceRepository.findAllByDeletedFalseOrderByName()).thenReturn(Collections.singletonList(repairServiceMaker()));
      
      Assert.assertEquals(1, mockRepairServiceService.listAllRepairServices().size());
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getRepairServiceByIdThrowsWhenNotFound() {
      
      mockRepairServiceService.getRepairServiceById(1);
      
   }
   
   @Test
   public void getRepairServiceByIdReturnsWhenFound() {
      
      RepairService repairService = repairServiceMaker();
      
      when(mockRepairServiceRepository.findById(1)).thenReturn(Optional.of(repairService));
      
      Assert.assertEquals(repairService, mockRepairServiceService.getRepairServiceById(1));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createNewRepairServiceThrowsWhenServiceExists() {
      
      RepairService repairService = repairServiceMaker();
      
      when(mockRepairServiceRepository.existsByNameAndDeletedFalse(repairService.getName())).thenReturn(true);
      
      mockRepairServiceService.createNewRepairService(repairService);
      
   }
   
   @Test
   public void createNewRepairServiceCreatesWhenServiceDoesntExist() {
      
      RepairService testRepairService = repairServiceMaker();
      
      when(mockRepairServiceRepository.existsByNameAndDeletedFalse(testRepairService.getName())).thenReturn(false);
      
      mockRepairServiceService.createNewRepairService(testRepairService);
      
      Mockito.verify(mockRepairServiceRepository, Mockito.times(1)).save(any(RepairService.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void editRepairServiceThrowsWhenServiceDoesNotExist() {
      
      RepairService testRepairService = repairServiceMaker();
      
//      Mockito.when(mockRepairServiceRepository.existsByNameAndDeletedFalse(testRepairService.getName()))
//              .thenReturn(false);
      
      mockRepairServiceService.editRepairService(testRepairService);
      
   }
   
   @Test
   public void editRepairServiceExecutesWhenServiceExists() {
      
      RepairService testRepairService = repairServiceMaker();
      
      when(mockRepairServiceRepository.existsByIdAndDeletedFalse(testRepairService.getId()))
              .thenReturn(true);
      
      mockRepairServiceService.editRepairService(testRepairService);
      
      Mockito.verify(mockRepairServiceRepository, Mockito.times(1)).save(any(RepairService.class));
      
   }
   
   @Test
   public void markServiceDeletedExecutes(){
      when(mockRepairServiceRepository.findByIdAndDeletedFalse(1)).thenReturn(repairServiceMaker());
      
      mockRepairServiceService.markServiceDeleted(1);
      
      verify(mockRepairServiceRepository, times(1)).save(any());
   }
   
}
