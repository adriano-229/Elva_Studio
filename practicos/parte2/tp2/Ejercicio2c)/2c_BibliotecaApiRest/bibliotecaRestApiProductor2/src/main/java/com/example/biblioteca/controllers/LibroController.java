package com.example.biblioteca.controllers;



import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
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

import com.example.biblioteca.entities.ArchivoPdf;
import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.service.ArchivoPdfService;
import com.example.biblioteca.serviceImpl.LibroServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/libros")
public class LibroController extends BaseControllerImpl<Libro, LibroServiceImpl>{
	
	@Autowired
	private ArchivoPdfService archivoService;
	
	@PostMapping(value = "savePdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> save(
			@RequestPart Libro libro,
	        @RequestPart("pdf") MultipartFile archivoPdf) {
		
		System.out.println("ESTOY EN SAVE DEL PRODUCTOR LIBRO CONTROLLER");
		
		try {
			if (archivoPdf == null || archivoPdf.isEmpty()) {
				System.out.println("ARCHIVO PDF esta vacio");
			}
			// Guardás el archivo en el disco o en la BD
		    String ruta = archivoService.almacenarPdf(archivoPdf);

		    ArchivoPdf archivo = new ArchivoPdf();
		    archivo.setNombrePdf(archivoPdf.getOriginalFilename());
		    archivo.setRutaPdf(ruta);
		    System.out.println("ESTOY EN EL PRODUCTOR - GUARDAR LIBRO");
	        System.out.println("RUTA LIBRO:" + ruta);
		    
	        archivo = archivoService.save(archivo);
	        
		    libro.setPdf(archivo);

		    return ResponseEntity.status(HttpStatus.OK).body(servicio.save(libro));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}
	
	@PutMapping(value = "updatePdf/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> update(
			@PathVariable Long id,
			@RequestPart Libro libro,
	        @RequestPart(value = "pdf", required = false) MultipartFile archivoPdf) {
		
		System.out.println("ESTOY EN UPDATE DEL PRODUCTOR LIBRO CONTROLLER");
		
		try {
			
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, libro, archivoPdf));
			
		} catch (Exception e) {
			System.out.println("Error guardando libro: " + e.getMessage());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id) {
	    try {
	        Libro libro = servicio.findByid(id);

	        // Crear un DTO con los datos del libro y la ruta completa al PDF
	        Map<String, Object> libroDTO = new HashMap<>();
	        libroDTO.put("id", libro.getId());
	        libroDTO.put("titulo", libro.getTitulo());
	        libroDTO.put("genero", libro.getGenero());
	        libroDTO.put("autores", libro.getAutores());
	        libroDTO.put("fecha", libro.getFecha());
	        libroDTO.put("paginas", libro.getPaginas());

	        if (libro.getPdf() != null) {
	            String rutaPdf = "http://localhost:9000/api/v1/libros/pdf/" + libro.getId();
	            libroDTO.put("nombrePdf", libro.getPdf().getNombrePdf());
	            libroDTO.put("rutaPdf", rutaPdf);
	            System.out.println("ESTOY EN EL PRODUCTOR");
		        System.out.println("RUTA LIBRO:" + rutaPdf);
	        }
	        
	        return ResponseEntity.status(HttpStatus.OK).body(libroDTO);

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND)
	            .body("{\"error\":\"Error. Por favor intente más tarde.\"}");
	    }
	}

	
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
	
	@GetMapping("/pdf/{id}")
	public ResponseEntity<Resource> descargarPdf(@PathVariable Long id) {
	    try {
	        Libro libro = servicio.findByid(id);

	        if (libro == null || libro.getPdf() == null) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                    .body(null);
	        }

	        // Obtener la ruta completa del archivo en disco
	        Path path = Paths.get(libro.getPdf().getRutaPdf());
	        Resource recurso = new UrlResource(path.toUri());

	        if (!recurso.exists() || !recurso.isReadable()) {
	            throw new RuntimeException("No se puede leer el archivo PDF");
	        }

	        return ResponseEntity.ok()
	                .contentType(MediaType.APPLICATION_PDF)
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "inline; filename=\"" + libro.getPdf().getNombrePdf() + "\"")
	                .body(recurso);

	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	
	

}
