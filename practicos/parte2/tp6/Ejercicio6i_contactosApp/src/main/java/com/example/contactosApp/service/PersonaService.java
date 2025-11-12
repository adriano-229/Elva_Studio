package com.example.contactosApp.service;

import java.util.List;

import com.example.contactosApp.domain.dto.PersonaDTO;

public interface PersonaService extends BaseService<PersonaDTO, Long>{
	
	List<PersonaDTO> findByNombreApellido(String filtro) throws Exception;

}
