package com.team14.carservice.controllers;

import com.team14.carservice.models.Event;
import com.team14.carservice.service.common.EmailService;
import com.team14.carservice.service.common.EventService;
import com.team14.carservice.utils.PdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/pdf")
public class PDFRestController {
   
   private final EmailService emailService;
   private final EventService eventService;
   
   @Autowired
   public PDFRestController(EmailService emailService,
                            EventService eventService) {
      this.emailService = emailService;
      this.eventService = eventService;
   }
   
   @GetMapping(produces = "application/pdf")
   
   public PdfView generate(Model model, @RequestParam int event) {
      
      model.addAttribute("event", eventService.getEventById(event));
      
      return new PdfView();
   }
   
   @GetMapping(value = "/email", produces = "application/pdf")
   public PdfView generateAndEmail(Model model, @RequestParam int event) {
      Event e = eventService.getEventById(event);
      
      model.addAttribute("event", e);
      model.addAttribute("email", e.getCustomerCar().getCustomerEmail());
      
      return new PdfView(emailService);
   }
}
