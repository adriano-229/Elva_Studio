package com.projects.gym.gym_app.controller;

import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projects.gym.gym_app.service.report.ReportService;

@Controller
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@GetMapping("/{facturaId}")
	public ResponseEntity<byte[]> generarReporte(@PathVariable String facturaId) {
	    try {
	        byte[] reportBytes = reportService.obtenerFacturaPDF(facturaId);

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        
	        // PARA DESCARGAR:
	        //headers.add("Content-Disposition", "attachment; filename=factura_" + facturaId + ".pdf");
	        headers.add("Content-Disposition", "inline; filename=factura_" + facturaId + ".pdf");

	        return new ResponseEntity<>(reportBytes, headers, HttpStatus.OK);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	    }
	}

}
