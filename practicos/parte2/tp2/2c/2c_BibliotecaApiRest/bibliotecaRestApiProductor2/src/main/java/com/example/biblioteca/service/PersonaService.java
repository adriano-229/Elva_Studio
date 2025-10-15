package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.biblioteca.entities.Persona;

public interface PersonaService extends BaseService<Persona, Long>{
	List<Persona> search(String filtro)throws Exception;
	
	Page<Persona> search(String filtro, Pageable pageable)throws Exception;

	Optional<Persona> searchByDni(int dni) throws Exception;
}
