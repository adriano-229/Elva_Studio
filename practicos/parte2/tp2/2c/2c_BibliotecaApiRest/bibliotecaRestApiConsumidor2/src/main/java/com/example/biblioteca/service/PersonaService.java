package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.domain.dto.PersonaDTO;

public interface PersonaService extends BaseService<PersonaDTO, Long>{
	
	List<PersonaDTO> search(String filtro)throws Exception;
	
	PersonaDTO searchByDni(int dni) throws Exception;
	
	//Page<Persona> search(String filtro, Pageable pageable)throws Exception;
}
