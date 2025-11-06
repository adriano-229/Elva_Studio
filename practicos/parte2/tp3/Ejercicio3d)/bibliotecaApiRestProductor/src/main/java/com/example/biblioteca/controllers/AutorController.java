package com.example.biblioteca.controllers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.serviceImpl.AutorServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api/v1/autores")
public class AutorController extends BaseControllerImpl<Autor, AutorServiceImpl>{
	
	@GetMapping("/searchByFiltro")
	public ResponseEntity<?> searchByNombreApellido(@RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.searchByNombreApellido(filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	

}
