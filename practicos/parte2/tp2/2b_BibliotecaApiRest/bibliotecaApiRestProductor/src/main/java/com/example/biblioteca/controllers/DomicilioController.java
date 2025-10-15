package com.example.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.entities.Domicilio;
import com.example.biblioteca.serviceImpl.DomicilioServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/ap1/v1/domicilios")
public class DomicilioController extends BaseControllerImpl<Domicilio, DomicilioServiceImpl>{
	
	@GetMapping("/searchByCalleYNumero")
	public ResponseEntity<?> searchByCalleNumeroYLocalidad(@RequestParam String calle, @RequestParam int numero, @RequestParam String denominacion){
		try {
			
			return servicio.buscarPorCalleNumeroYLocalidad(calle, numero, denominacion)
	                   .<ResponseEntity<?>>map(domicilio -> ResponseEntity.ok(domicilio))
	                   .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                                  .body("{\"error\": \"No se encontró un domicilio con esa calle y número.\"}"));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}

}
