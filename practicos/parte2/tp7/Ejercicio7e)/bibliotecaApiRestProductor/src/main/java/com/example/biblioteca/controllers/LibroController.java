package com.example.biblioteca.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.serviceImpl.LibroServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl>{
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscar(filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/searchByAutor")
	public ResponseEntity<?> searchByAutor(@RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorAutor(filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/searchByTituloOGenero")
	public ResponseEntity<?> searchByTituloOGenero(@RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorTituloOGenero(filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	@GetMapping("/searchByTitulo")
	public ResponseEntity<?> searchByTituloo(@RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.buscarPorTitulo(filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
}
