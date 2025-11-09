package com.example.mycar.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mycar.dto.DocumentacionDTO;
import com.example.mycar.entities.Documentacion;
import com.example.mycar.services.impl.DocumentacionServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/documentacion")
public class DocumentacionController extends BaseControllerImpl<Documentacion, DocumentacionDTO, DocumentacionServiceImpl>{

	
	@PostMapping(value = "saveConDocumento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(
			@RequestPart("documentacion") DocumentacionDTO documentacion,
	        @RequestPart("pdf") MultipartFile archivo) {
		
		try {
			if (archivo == null || archivo.isEmpty()) {
				System.out.println("ARCHIVO PDF esta vacio");
			}
			
			String ruta = servicio.almacenarPdf(archivo);
			
			documentacion.setPathArchivo(ruta);
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(documentacion));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}
	
	@PutMapping(value = "updateConDocumento/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(
			@PathVariable Long id,
			@RequestPart DocumentacionDTO documentacion,
	        @RequestPart(value = "pdf", required = false) MultipartFile archivo) {
		
		try {
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, documentacion, archivo));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}
	
	
}
