package com.example.mycar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.mycar.dto.BaseDTO;
import com.example.mycar.entities.Base;
import com.example.mycar.services.impl.BaseServiceImpl;

import jakarta.validation.Valid;

public abstract class BaseControllerImpl<E extends Base, D extends BaseDTO, S extends BaseServiceImpl<E, D, Long>> implements BaseController<D, Long>{
	
	@Autowired
	protected S servicio;
	
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
	}
	
	/*@GetMapping("/paged")
	public ResponseEntity<?> getAll(Pageable pageable){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.findAll(pageable));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
	}*/
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOne(@PathVariable Long id){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.findById(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
	}
	
	@PostMapping("")
	public ResponseEntity<?> save(@RequestBody D dto){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.save(dto));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable Long id,@Valid @RequestBody D dto){
		try {
			return ResponseEntity.status(HttpStatus.OK).body(servicio.update(id, dto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(servicio.delete(id));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. por favor intente más tarde.\"}");
		}
		
	}
	
	/*@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
	    try {

	    	boolean eliminado = servicio.delete(id);
	        return ResponseEntity.ok(Map.of("deleted", eliminado));
	        
	    } catch (EntidadRelacionadaException e) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	                .body(Map.of("error", e.getMessage()));

	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", e.getMessage() != null ? e.getMessage() : "Error. por favor intente más tarde."));
	    }
	}*/	
}