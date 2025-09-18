package elva.studio.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/factura")
public class FacturaControlador {
	
	//generar pdf
	@GetMapping("/pdf")
	public void generarPdf(HttpServletResponse response, HttpSession session) throws IOException {
	    //response.setContentType("application/pdf");
	    //response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");

	    @SuppressWarnings("unchecked")
	    ModelMap datosFactura = (ModelMap) session.getAttribute("datosFactura");

	    if (datosFactura == null) {
	        //response.getWriter().write("No hay datos de factura disponibles");
	    	response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	        return;
	    }
	    
	    response.setContentType("application/pdf");
	    response.setHeader("Content-Disposition", "attachment; filename=factura.pdf");
	    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Expires", "0");
	    
	    try (OutputStream out = response.getOutputStream()) {
	        com.lowagie.text.Document document = new com.lowagie.text.Document();
	        com.lowagie.text.pdf.PdfWriter.getInstance(document, out);

	        document.open();

	        // Título
	        document.add(new com.lowagie.text.Paragraph("FACTURA B"));
	        document.add(new com.lowagie.text.Paragraph(" ")); // Espacio

	        // Datos del socio
	        document.add(new com.lowagie.text.Paragraph("Fecha: " + datosFactura.get("fecha")));
	        document.add(new com.lowagie.text.Paragraph("Cliente: " 
	            + datosFactura.get("nombre") + " " + datosFactura.get("apellido")));
	        document.add(new com.lowagie.text.Paragraph("Documento: " + datosFactura.get("documento")));
	        document.add(new com.lowagie.text.Paragraph("Dirección: " + datosFactura.get("direccion")));
	        document.add(new com.lowagie.text.Paragraph(" "));

	        // Detalle
	        document.add(new com.lowagie.text.Paragraph("Valor de cuota: $" + datosFactura.get("valorCuota")));
	        document.add(new com.lowagie.text.Paragraph("Total: $" + datosFactura.get("total")));
	        document.add(new com.lowagie.text.Paragraph("IVA (21%): $" + datosFactura.get("iva")));
	        document.add(new com.lowagie.text.Paragraph("Precio final: $" + datosFactura.get("precioFinal")));

	        document.close();
	    }
	}

}
