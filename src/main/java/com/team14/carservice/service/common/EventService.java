package com.team14.carservice.service.common;

import com.team14.carservice.models.Event;
import com.team14.carservice.models.dto.SearchEventDto;

import java.text.ParseException;
import java.util.List;

public interface EventService {
   
   List<Event> listAllEvents();
   
   List<Event> listAllEventsByCustomer(Integer customerId);

   List<Event> listAllEventsByCar(Integer carId);

   Event createNewEvent(Event event);
   
   Event editEvent(Event event);

   Event getEventById(Integer id);
   
   List<Event> getAllByCriteria(SearchEventDto dto) throws ParseException;

}
