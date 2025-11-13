package com.example.biblioteca.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.entities.Localidad;
import com.example.biblioteca.serviceImpl.LocalidadServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/localidades")
public class LocalidadController extends BaseControllerImpl<Localidad, LocalidadServiceImpl>{
	
	@GetMapping("/searchByDenominacion")
	public ResponseEntity<?> searchByDenominacion(@RequestParam String denominacion){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByDenominacion(denominacion));
			
		} catch (Exception e) {
			System.out.println("EXCEPCION");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	
}
