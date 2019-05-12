package com.team14.carservice;

import com.team14.carservice.models.vehicles.Car;
import com.team14.carservice.repository.vehicles.CarRepository;
import com.team14.carservice.service.vehicles.CarServiceImpl;
import com.team14.carservice.service.vehicles.ModelServiceImpl;
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

import static com.team14.carservice.Helpers.carMaker;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTests {
   
   @Mock
   CarRepository mockRepository;
   
   @InjectMocks
   CarServiceImpl carService;
   
   @InjectMocks
   ModelServiceImpl modelService;
   
   @Test(expected = ResponseStatusException.class)
   public void getAllThrowsWhenEmpty() {
      
      carService.getAllCars();
      
   }
   
   @Test
   public void getAllReturnsAllWhenNotEmpty() {
      
      Mockito.when(mockRepository.getAllByDeletedFalse()).thenReturn(Optional.of(Collections.singletonList(carMaker())));
      
      Assert.assertEquals(1, carService.getAllCars().size());
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getByIdThrowsWhenNotFound() {
      
      carService.getById(1);
   }
   
   @Test
   public void getByIdReturnsWhenFound() {
      
      Car car = carMaker();
      
      Mockito.when(mockRepository.getByIdAndDeletedFalse(1)).thenReturn(Optional.of(car));
      
      Assert.assertEquals(carService.getById(1), car);
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createCarThrowsWhenCarExists() {
      
      Car car = carMaker();
      
      Mockito.when(mockRepository.getByLicensePlateAndDeletedFalse(car.getLicensePlate()))
              .thenReturn(Optional.of(car));
      
      carService.createCar(car);
   }
   
   @Test
   public void createCarExecutesWhenCarDoesNotExist() {
      
      Car car = carMaker();
      
      Mockito.when(mockRepository.getByLicensePlateAndDeletedFalse(car.getLicensePlate()))
              .thenReturn(Optional.empty());
      
      carService.createCar(car);
      
      verify(mockRepository, times(1)).save(any(Car.class));
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createCarThrowsWhenCarIsNull() {
      
      carService.createCar(null);
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void editCarThrowsWhenCarIsNull() {
      
      carService.edit(null);
      
   }
   
   @Test
   public void editCarExecutesWhenCarIsNotNull() {
      
      Car car = carMaker();
      
      carService.edit(car);
      
      verify(mockRepository, times(1)).save(any(Car.class));
   }
   
   @Test
   public void deleteCarDeletes(){
      carService.delete(carMaker());
      
      verify(mockRepository, times(1)).delete(any(Car.class));
   }
   
   
}
