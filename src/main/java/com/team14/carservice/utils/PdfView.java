package com.team14.carservice.utils;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.team14.carservice.models.Customer;
import com.team14.carservice.models.DetailedRepairService;
import com.team14.carservice.models.Event;
import com.team14.carservice.models.vehicles.CustomerCar;
import com.team14.carservice.service.common.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class PdfView extends AbstractPdfView {
   
   public PdfView() {
   }
   
   @Autowired
   public PdfView(EmailService emailService) {
      super(emailService);
   }
   
   @Override
   protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer, HttpServletRequest request, HttpServletResponse response) throws Exception {

//      response.setHeader("Content-Disposition", "attachment;");
   
      Event event = (Event) model.get("event");
      List<DetailedRepairService> services = event.getDetailedRepairServices();
      Customer customer = event.getCustomerCar().getCustomer();
      CustomerCar customerCar = event.getCustomerCar();
   
      DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
      Date date = new Date();
      String today = df.format(date);
   
//      response.setHeader("Content-Disposition", "inline;filename=\"" + today + customer.getName() + "\"");
//      response.setHeader("Content-Disposition", "attachment;");
//      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "inline;filename=\"car-service-invoice.pdf\"");
      
//      BaseFont base = BaseFont.createFont("src/main/resources/static/fonts/Raleway.ttf", BaseFont.IDENTITY_H, true);
//      BaseFont base = BaseFont.createFont("src/main/resources/static/fonts/Raleway.ttf", BaseFont.IDENTITY_H, true);
//      Font raleway = new Font(base, 11f, Font.NORMAL);
      Font raleway = FontFactory.getFont(FontFactory.HELVETICA);
      document.addTitle("DriveSafe " + event.getDate().toString().substring(0, 10) + " " + customer.getName());
      
      //common details
      document.add(getInvoiceHeader(event, today, raleway));
      
      //client and car details
      document.add(clientAndCarDetails(customer, customerCar, raleway));
      
      //services table
      document.add(fillServicesTable(event, services, raleway));
      
      //comment table
      document.add(comment(event, raleway));
      
      
   }
   
   private PdfPTable clientAndCarDetails(Customer customer, CustomerCar customerCar, Font raleway) {
      PdfPTable customerDetailsTable = new PdfPTable(2);
      customerDetailsTable.setWidthPercentage(80.0f);
      
      
      PdfPCell cell = new PdfPCell();
      cell.setFixedHeight(100);
      cell.setBorder(0);
      cell.setPaddingBottom(7);
      
      raleway.setSize(14);
      cell.setPhrase(new Phrase("Customer information:" + System.lineSeparator() +
              "Name: " + customer.getName() + System.lineSeparator() +
              "Email: " + customer.getEmail() + System.lineSeparator() +
              "Phone: " + customer.getPhone() + System.lineSeparator(), raleway));
      customerDetailsTable.addCell(cell);
      
      cell.setPhrase(new Phrase("Vehicle information:" + System.lineSeparator() +
              "Make: " + customerCar.getCarMakeName() + System.lineSeparator() +
              "Model: " + customerCar.getCarModelName() + System.lineSeparator() +
              "Year: " + customerCar.getCarYear() + System.lineSeparator() +
              "License Plate: " + customerCar.getCar().getLicensePlate() + System.lineSeparator() +
              "VIN: " + customerCar.getCar().getVin() + System.lineSeparator(), raleway));
      
      customerDetailsTable.addCell(cell).setHorizontalAlignment(2);
      return customerDetailsTable;
   }
   
   private PdfPTable comment(Event event, Font raleway) {
      PdfPTable table = new PdfPTable(1);
      table.setTotalWidth(80.0f);
      table.setSpacingBefore(60);
      
      PdfPCell cell = new PdfPCell();
      cell.setBorder(1);
      cell.setNoWrap(false);
      
      raleway.setSize(12);
      raleway.setStyle(Font.ITALIC);
      Phrase phrase = new Phrase(event.getComment(), raleway);
      
      cell.setPhrase(phrase);
      table.addCell(new PdfPCell(new Phrase(event.getComment(), raleway)));
      
      return table;
   }
   
   private PdfPTable getInvoiceHeader(Event event, String today, Font raleway) {
      PdfPTable table = new PdfPTable(2);
      table.setWidthPercentage(80.0f);
      table.setPaddingTop(50);
      
      PdfPCell cell = new PdfPCell();
      cell.setFixedHeight(100);
      cell.setBorder(0);
      
      raleway.setSize(32);
      raleway.setStyle(Font.BOLD);
      Phrase phrase = new Phrase("DRIVE SAFE", raleway);
      
      cell.setPhrase(phrase);
      table.addCell(cell);
      
      raleway.setSize(20);
      raleway.setStyle(Font.NORMAL);
      
      phrase = new Phrase("Invoice No: " + event.getId() + System.lineSeparator() + today, raleway);
      
      cell.setPhrase(phrase);
      table.addCell(cell).setHorizontalAlignment(2);
      
      return table;
   }
   
   private PdfPTable fillServicesTable(Event event, List<DetailedRepairService> services, Font raleway) throws DocumentException {
      PdfPTable table = new PdfPTable(3);
      table.setWidths(new float[] {35, 50, 15});
//      table.setSpacingBefore(30);
      
      raleway.setColor(BaseColor.BLACK);
      raleway.setSize(16);
      
      PdfPCell cell = new PdfPCell();
      cell.setBackgroundColor(new BaseColor(142, 219, 219));
      cell.setPadding(5);
      
      cell.setPhrase(new Phrase("Service Name", raleway));
      table.addCell(cell);
      
      cell.setPhrase(new Phrase("Comment", raleway));
      table.addCell(cell);
      
      cell.setPhrase(new Phrase("Price", raleway));
      table.addCell(cell);
      
      PdfPCell serviceCell = new PdfPCell();
      serviceCell.setPadding(2);
      serviceCell.setPaddingBottom(3);
      serviceCell.setPaddingTop(2);
      
      raleway.setColor(BaseColor.BLACK);
      
      for (DetailedRepairService service : services) {
         raleway.setStyle(Font.NORMAL);
         raleway.setSize(15);
         serviceCell.setHorizontalAlignment(0);
         serviceCell.setPaddingLeft(5);
         serviceCell.setPhrase(new Phrase(service.getRepairService().getName(), raleway));
         table.addCell(serviceCell);
         
         raleway.setSize(12);
         raleway.setStyle(Font.ITALIC);
         serviceCell.setNoWrap(false);
         serviceCell.setPhrase(new Phrase(service.getServiceComment(), raleway));
         table.addCell(serviceCell);
         
         raleway.setSize(15);
         raleway.setStyle(Font.NORMAL);
         serviceCell.setPhrase(new Phrase(service.getPrice() + "$", raleway));
         table.addCell(serviceCell);
         
         
      }
      
      cell.setBorder(0);
      cell.setPhrase(new Phrase(""));
      cell.setBackgroundColor(BaseColor.WHITE);
      table.addCell(cell);
      cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
      cell.setPhrase(new Phrase("Total:"));
      table.addCell(cell);
      
      cell.setBorder(1);
      cell.setPhrase(new Phrase(event.getTotalPrice().toString() + "$", raleway));
      table.addCell(cell);
      
      return table;
   }
   
}
