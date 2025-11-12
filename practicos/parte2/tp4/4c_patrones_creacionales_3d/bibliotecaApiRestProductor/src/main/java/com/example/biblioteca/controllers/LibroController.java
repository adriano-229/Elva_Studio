package com.example.biblioteca.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.entities.LibroFactory;
import com.example.biblioteca.entities.dto.LibroDTO;
import com.example.biblioteca.serviceImpl.LibroServiceImpl;
import com.example.biblioteca.strategy.BusquedaLibroContext;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl>{
	
	@Autowired
	private LibroFactory libroFactory;
	
	@Autowired
	private BusquedaLibroContext contexto;
	
	@PostMapping(value = "/saveLibro", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(
			@RequestPart("libro") String libroJson,
	        @RequestPart("pdf") MultipartFile archivoPdf) {
		
		System.out.println("ESTOY EN SAVE DEL PRODUCTOR LIBRO CONTROLLER");
		
		try {
			
			ObjectMapper mapper = new ObjectMapper();
	        LibroDTO libroDto = mapper.readValue(libroJson, LibroDTO.class);

	        System.out.println("TIPO RECIBIDO: " + libroDto.getTipo());
	        
	        libroDto.setArchivoPdf(archivoPdf);
	        
			
			/*LibroDTO libroDto = LibroDTO.builder()
					.titulo(libro.getTitulo())
					.genero(libro.getGenero())
					.paginas(libro.getPaginas())
					.fecha(libro.getFecha())
					.tipo(libro.getTipo())
					.autores(libro.getAutores())
					.archivoPdf(archivoPdf)
					.cantEjemplares(libro.getCantEjemplares())
					.build();*/
			
			Libro libroNuevo = libroFactory.crearLibro(libroDto);
			
			System.out.println("libro nuevo: " + libroNuevo);
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(libroNuevo));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}
	
	@PutMapping(value = "/updateLibro/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(
			@PathVariable Long id,
			@RequestPart("libro") String libroJson,
	        @RequestPart(value = "pdf", required = false) MultipartFile archivoPdf) {
		
		System.out.println("ESTOY EN UPDATE DEL PRODUCTOR LIBRO CONTROLLER");
		
		try {
			
			 ObjectMapper mapper = new ObjectMapper();
		     LibroDTO libroDto = mapper.readValue(libroJson, LibroDTO.class);
			
		     if (archivoPdf != null) {
		    	 libroDto.setArchivoPdf(archivoPdf);
		     }

			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, libroDto));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}
	
	@GetMapping("/search")
	public ResponseEntity<?> search(@RequestParam String tipo, @RequestParam String filtro){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(contexto.buscar(tipo, filtro));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	/*@GetMapping("/search")
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
	}*/
	
}
