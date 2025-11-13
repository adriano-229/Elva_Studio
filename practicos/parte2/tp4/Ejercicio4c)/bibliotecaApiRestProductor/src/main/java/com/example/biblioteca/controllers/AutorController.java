package com.example.biblioteca.controllers;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.biblioteca.adapter.AutorAdapter;
import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.entities.dto.AutorDTO;
import com.example.biblioteca.serviceImpl.AutorServiceImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path="/api/v1/autores")
public class AutorController extends BaseControllerImpl<Autor, AutorServiceImpl>{
	
	@PostMapping("/saveAutor")
    public ResponseEntity<?> saveAutor(@RequestBody AutorDTO autorDTO) {
        try {
            Autor autor = AutorAdapter.toEntity(autorDTO);
            Autor saved = servicio.save(autor);
            AutorDTO responseDTO = AutorAdapter.toDTO(saved);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
	
	@GetMapping("/clonar/{id}")
	public ResponseEntity<?> clonarAutor(@PathVariable Long id){
		try {
			Autor autorClonado = servicio.clonarAutor(id);
            AutorDTO dto = AutorAdapter.toDTO(autorClonado);
			return ResponseEntity.status(HttpStatus.OK).body(dto);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
		
	}
	
	@GetMapping("/searchByFiltro")
	public ResponseEntity<?> searchByNombreApellido(@RequestParam String filtro){
		try {
			List<Autor> autores = servicio.searchByNombreApellido(filtro);
            List<AutorDTO> autoresDTO = autores.stream()
                    .map(AutorAdapter::toDTO)
                    .toList();
			return ResponseEntity.status(HttpStatus.OK).body(autoresDTO);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(("{\"error\": \"" + e.getMessage() + "\"}"));
		}
	}
	
	

}
