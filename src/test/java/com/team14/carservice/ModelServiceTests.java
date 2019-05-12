package com.team14.carservice;

import com.team14.carservice.models.vehicles.Model;
import com.team14.carservice.repository.vehicles.ModelRepository;
import com.team14.carservice.service.vehicles.ModelServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ModelServiceTests {
   
   @Mock
   ModelRepository repository;
   
   @InjectMocks
   ModelServiceImpl modelService;
   
   @Test
   public void getAllExecutes(){
      modelService.getAll();
      
      verify(repository, times(1)).findAll();
   }
   
   @Test
   public void getAllByManufacturerExecutes(){
      modelService.getAllByManufacturer(any());
      
      verify(repository, times(1)).getAllByManufacturer(any());
   }
   
   @Test
   public void getAllByManufacturerNameExecutes(){
      modelService.getAllByManufacturerName(any());
      
      verify(repository, times(1)).getAllByManufacturer_Name(any());
   }
   
   @Test
   public void getAllByIdExecutes(){
      when(repository.getById(1)).thenReturn(new Model());
   
      modelService.getById(1);
      
      verify(repository, times(1)).getById(1);
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getAllByIdThrowsWhenNull(){
      when(repository.getById(1)).thenReturn(null);
      
      modelService.getById(1);
      
      verify(repository, times(1)).getById(1);
   }
}
