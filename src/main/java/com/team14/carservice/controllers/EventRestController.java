package com.team14.carservice.controllers;

import com.team14.carservice.models.Event;
import com.team14.carservice.models.dto.SearchEventDto;
import com.team14.carservice.service.common.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/r-event")
public class EventRestController {
   
   private final EventService service;
   
   @Autowired
   public EventRestController(EventService service) {
      this.service = service;
   }
   
   @GetMapping("/")
   public List<Event> listAllEvents(@RequestParam(value = "id", required = false) Integer id) {
      
      if (id != null) return Collections.singletonList(service.getEventById(id));
      
      else return service.listAllEvents();
   }
   
   @GetMapping("/get")
   public List<Event> listAllEventsByCustomer(@RequestParam(value = "customerId") Integer customerId) {
      return service.listAllEventsByCustomer(customerId);
   }

   @GetMapping("/getByCar")
   public List<Event> listAllEventsByCar(@RequestParam(value = "carId") Integer carId) {
      return service.listAllEventsByCar(carId);
   }
   
   @PostMapping("/new")
   public Event createNewEvent(@RequestBody @Valid Event event) {
      return service.createNewEvent(event);
   }
   
   @PutMapping("/edit")
   public Event editEvent(@RequestBody Event event) {
      return service.editEvent(event);
   }

   @GetMapping("/for")
   public List<Event> getAllEventsForCustomer(SearchEventDto searchEventDto) {
      try {
         return service.getAllByCriteria(searchEventDto);
      }catch (ParseException p){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Parsing error");
      }catch (ResponseStatusException r){
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid search criteria.");
      }
   }
   
   @PutMapping("/done")
   public void markFinalized(@RequestParam int id){
      Event event = service.getEventById(id);
      event.setFinalized(true);
      service.editEvent(event);
   }
}
