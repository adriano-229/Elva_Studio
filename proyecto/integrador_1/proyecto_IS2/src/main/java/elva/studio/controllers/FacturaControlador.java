package elva.studio.controllers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import elva.studio.entities.Factura;
import elva.studio.enumeration.EstadoFactura;
import elva.studio.services.FacturaService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/factura")
public class FacturaControlador {
	
	@Autowired
	private FacturaService svcFactura;
	
	//para probar que me devuelve bien las facturas del socio
	/*
	@GetMapping("/socio/{idSocio}")
    public ResponseEntity<List<Factura>> listarFacturasPorSocio(@PathVariable Long idSocio) {
        List<Factura> facturas = svcFactura.listarFacturaPorSocioActivas(idSocio);
        System.out.println("Facturas del socio activas: "+idSocio+" son: "+facturas);
        return ResponseEntity.ok(facturas);
    }
	
	@GetMapping("/socio/{estado}")
    public ResponseEntity<List<Factura>> listarFacturasPorEstado(@PathVariable EstadoFactura estado) {
        List<Factura> facturas = svcFactura.listarFacturaPorEstado(estado);
        System.out.println("Facturas en estado: "+estado+"son: "+facturas);
        return ResponseEntity.ok(facturas);
    }*/

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
