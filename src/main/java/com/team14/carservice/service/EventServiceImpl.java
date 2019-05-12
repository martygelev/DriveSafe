package com.team14.carservice.service;

import com.team14.carservice.models.Event;
import com.team14.carservice.models.dto.SearchEventDto;
import com.team14.carservice.repository.CustomerRepository;
import com.team14.carservice.repository.EventRepository;
import com.team14.carservice.repository.vehicles.CarRepository;
import com.team14.carservice.repository.vehicles.CustomerCarRepository;
import com.team14.carservice.service.common.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static com.team14.carservice.utils.EventSpecification.*;
import static com.team14.carservice.utils.UserIdentification.getLoggedInUserEmail;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class EventServiceImpl implements EventService {
   
   private final EventRepository eventRepository;
   private final CustomerRepository customerRepository;
   private final CarRepository carRepository;
   private final CustomerCarRepository customerCarRepository;
   
   @Autowired
   public EventServiceImpl(CustomerCarRepository customerCarRepository,
                           EventRepository eventRepository,
                           CustomerRepository customerRepository,
   CarRepository carRepository) {
      this.eventRepository = eventRepository;
      this.customerRepository = customerRepository;
      this.customerCarRepository = customerCarRepository;
      this.carRepository = carRepository;
   }
   
   @Override
   public List<Event> listAllEvents() {
      return Optional.ofNullable(eventRepository.findAll())
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, "No events currently added."));
   }
   
   public Event getEventById(Integer id) {
      return eventRepository.findById(id)
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, String.format("No events with id %d.", id)));

   }
   
   
   public List<Event> getAllByCriteria(SearchEventDto dto) {
      
      if (dto == null) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Search criteria are empty!");
      }
      
      if (dto.getCustomer() == null) {
         dto.setCustomer(customerRepository.findByEmail(getLoggedInUserEmail()).getId());
      }
      
      return eventRepository.findAll(where(customerIs(dto.getCustomer())
              .and(customerCarIs(dto.getCustomerCar())
                      .and(eventStateIs(dto.getFinalized()))
                      .and(dateIsBefore(dto.getTo()))
                      .and(dateIsAfter(dto.getFrom())))));
      
   }

   @Override
   public List<Event> listAllEventsByCustomer(Integer customerId) {
      
      customerRepository.findById(customerId)
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, String.format("Customer with id %d does not exist.", customerId)));
      
      return eventRepository.getAllByCustomer(customerId)
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, String.format("No events for customer with id %d.", customerId)));
      
   }

   @Override
   public List<Event> listAllEventsByCar(Integer customerId) {

      carRepository.findById(customerId)
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, String.format("car with id %d does not exist.", customerId)));


      return eventRepository.getAllByCar(customerId)
              .orElseThrow(() -> new ResponseStatusException
                      (HttpStatus.NOT_FOUND, String.format("No events for car with id %d.", customerId)));

   }
   
   @Override
   public Event createNewEvent(Event event) {
      
      if (event == null)
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Event is null.");
      
      event.setCustomerCar(customerCarRepository.getFirstById(event.getCustomerCar().getId()));
      event.setFinalized(false);
      
      return eventRepository.save(event);
   }
   
   @Override
   public Event editEvent(Event event) {
      
      if (event == null || getEventById(event.getId()) == null)
         throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event not found.");
      
      return eventRepository.save(event);
   }
   
}
