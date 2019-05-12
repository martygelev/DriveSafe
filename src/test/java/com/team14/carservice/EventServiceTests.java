package com.team14.carservice;

import com.team14.carservice.models.Customer;
import com.team14.carservice.models.Event;
import com.team14.carservice.models.dto.SearchEventDto;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.EventRepository;
import com.team14.carservice.repository.vehicles.CustomerCarRepository;
import com.team14.carservice.service.EventServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Optional;

import static com.team14.carservice.Helpers.customerMaker;
import static com.team14.carservice.Helpers.eventMaker;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EventServiceTests {
   
   @Mock
   EventRepository mockEventRepository;
   
   @Mock
   UserDetailsManager userDetailsManager;
   
   @Mock
   CustomerRepository mockCustomerRepository;
   
   @Mock
   CustomerCarRepository mockCustomerCarRepository;
   
   @InjectMocks
   EventServiceImpl mockEventService;
   
   @Test(expected = ResponseStatusException.class)
   public void listAllThrowsWhenEmpty() {
      
      when(mockEventRepository.findAll()).thenReturn(null);
      
      mockEventService.listAllEvents();
      
   }
   
   @Test
   public void listAllReturnsAllWhenNotEmpty() {
      
      when(mockEventRepository.findAll()).thenReturn(Collections.singletonList(eventMaker()));
      
      Assert.assertEquals(1, mockEventService.listAllEvents().size());
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getEventByIdThrowsWhenNotFound() {
      
      mockEventService.getEventById(1);
      
   }
   
   @Test
   public void getEventByIdReturnsWhenFound() {
      
      Event event = eventMaker();
      
      when(mockEventRepository.findById(1)).thenReturn(Optional.of(event));
      
      Assert.assertEquals(Optional.of(event), mockEventRepository.findById(1));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void listAllEventsByCustomerThrowsWhenCustomerDoesNotExist() {
      
      mockEventService.listAllEventsByCustomer(1);
      
   }
   
   @Test
   public void listAllEventsByCustomerExecutesAllWhenCustomerExists() {
      
      Customer testCustomer = customerMaker();
      
      when(mockCustomerRepository.findById(1)).thenReturn(Optional.of(testCustomer));
      
      when(mockEventRepository.getAllByCustomer(1)).thenReturn(Optional.of(Collections.singletonList(eventMaker())));
      
      Assert.assertEquals(1, mockEventService.listAllEventsByCustomer(1).size());
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void listAllEventsByCustomerThrowsAllWhenListIsEmpty() {
      
      mockEventService.listAllEventsByCustomer(1);
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void createNewEventThrowsAllWhenEventIsNull() {
      
      mockEventService.createNewEvent(null);
      
   }
   
   @Test
   public void createNewEventExecutesAllWhenEventIsNotNull() {
      
      Event event = eventMaker();
      
      mockEventService.createNewEvent(event);
      
      Mockito.verify(mockEventRepository, Mockito.times(1)).save(any(Event.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void editEventThrowsAllWhenEventIsNull() {
      
      mockEventService.editEvent(null);
      
   }
   
   @Test
   public void editEventExecutesAllWhenEventIsNotNull() {
      
      when(mockEventRepository.findById(1)).thenReturn(Optional.of(eventMaker()));
      
      mockEventService.editEvent(eventMaker());
      
      Mockito.verify(mockEventRepository, Mockito.times(1)).save(any(Event.class));
      
   }
   
   @Test(expected = ResponseStatusException.class)
   public void getAllByCriteriaThrowsWhenDtoIsNull() {
      
      mockEventService.getAllByCriteria(null);
      
   }
   
   @Test
   public void getAllByCriteriaSetsCurrentUserIfNull() {
      
      SearchEventDto dto = new SearchEventDto();
      dto.setCustomerCar(1);
      
      Authentication authentication = Mockito.mock(Authentication.class);
      SecurityContext securityContext = Mockito.mock(SecurityContext.class);
      Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
      SecurityContextHolder.setContext(securityContext);
      when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("test");
      
      when(mockCustomerRepository.findByEmail("test")).thenReturn(customerMaker());
      
      mockEventService.getAllByCriteria(dto);
      
      verify(mockCustomerRepository, times(1)).findByEmail("test");
      
   }
   
//   @Test
//   public void getAllByCriteriaExecutesWhenDtoIsNotNull() {
//
//      SearchEventDto dto = new SearchEventDto();
//      dto.setCustomerCar(1);
//      dto.setCustomer(1);
//
//      mockEventService.getAllByCriteria(dto);
//
//      verify(mockEventRepository, times(1)).findAll(any(Specification.class));
//   }
   
}
