package com.team14.carservice.utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.team14.carservice.models.Event;
import com.team14.carservice.service.common.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.AbstractView;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Service
public abstract class AbstractPdfView extends AbstractView {
   private EmailService emailService;
   
   AbstractPdfView() {
      setContentType("application/pdf");
   }
   
   public AbstractPdfView(EmailService emailService) {
      setContentType("application/pdf");
      this.emailService = emailService;

   }
   
   @Override
   protected boolean generatesDownloadContent() {
      return true;
   }
   
   @Override
   protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
      
      ByteArrayOutputStream out = createTemporaryOutputStream();
      
      Document document = new Document(PageSize.A4.rotate(), 35, 35, 50, 36);
      PdfWriter writer = PdfWriter.getInstance(document, out);
      prepareWriter(model, writer, request);
      buildPdfMetadata(model, document, request);
      
      document.open();
      buildPdfDocument(model, document, writer, request, response);
      document.close();
      
      if (model.get("email") == null) {
         writeToResponse(response, out);
         
      } else {
         Event event = (Event) model.get("event");
         DataSource aAttachment = new ByteArrayDataSource(out.toByteArray(),
                 "application/octet-stream");
         MimeBodyPart attachmentPart = new MimeBodyPart();
         attachmentPart.setDataHandler(new DataHandler(aAttachment));
         response.setContentType(getContentType());
         response.setContentLength(out.size());
         
         byte[] result = out.toByteArray();
         
         emailService.sendEmailWithInvoice(
                 event.getCustomerCar().getCustomerEmail(), "Invoice",
                 "Invoice for your visit at DriveSafe on " + event.getDate().toString().substring(0,10), result);
         
         out.flush();
      }
      
   }
   
   
   /**
    * Prepare the given PdfWriter. Called before building the PDF document,
    * that is, before the call to {@code Document.open()}.
    * <p>Useful for registering a page event listener, for example.
    * The default implementation sets the viewer preferences as returned
    * by this class's {@code getViewerPreferences()} method.
    *
    * @param model   the model, in case meta information must be populated from it
    * @param writer  the PdfWriter to prepare
    * @param request in case we need locale etc. Shouldn't look at attributes.
    * @throws DocumentException if thrown during writer preparation
    */
   protected void prepareWriter(Map<String, Object> model, PdfWriter writer, HttpServletRequest request) throws DocumentException {
      writer.setViewerPreferences(getViewerPreferences());
   }
   
   /**
    * Return the viewer preferences for the PDF file.
    * <p>By default returns {@code AllowPrinting} and
    * {@code PageLayoutSinglePage}, but can be subclassed.
    * The subclass can either have fixed preferences or retrieve
    * them from bean properties defined on the View.
    *
    * @return an int containing the bits information against PdfWriter definitions
    */
   protected int getViewerPreferences() {
      return PdfWriter.ALLOW_PRINTING | PdfWriter.PageLayoutSinglePage;
   }
   
   /**
    * Populate the iText Document's meta fields (author, title, etc.).
    * <br>Default is an empty implementation. Subclasses may override this method
    * to add meta fields such as title, subject, author, creator, keywords, etc.
    * This method is called after assigning a PdfWriter to the Document and
    * before calling {@code document.open()}.
    *
    * @param model    the model, in case meta information must be populated from it
    * @param document the iText document being populated
    * @param request  in case we need locale etc. Shouldn't look at attributes.
    */
   protected void buildPdfMetadata(Map<String, Object> model, Document document, HttpServletRequest request) {
   }
   
   /**
    * Subclasses must implement this method to build an iText PDF document,
    * given the model. Called between {@code Document.open()} and
    * {@code Document.close()} calls.
    * <p>Note that the passed-in HTTP response is just supposed to be used
    * for setting cookies or other HTTP headers. The built PDF document itself
    * will automatically get written to the response after this method returns.
    *
    * @param model    the model Map
    * @param document the iText Document to add elements to
    * @param writer   the PdfWriter to use
    * @param request  in case we need locale etc. Shouldn't look at attributes.
    * @param response in case we need to set cookies. Shouldn't write to it.
    * @throws Exception any exception that occurred during document building
    */
   protected abstract void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
                                            HttpServletRequest request, HttpServletResponse response) throws Exception;
}
