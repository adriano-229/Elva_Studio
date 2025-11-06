package com.example.biblioteca.dao;

import java.util.List;

import com.example.biblioteca.domain.dto.PersonaDTO;

public interface PersonaRestDao extends BaseRestDao<PersonaDTO, Long>{
	
	List<PersonaDTO> buscarPorNombreOApellido(String filtro) throws Exception;
	PersonaDTO buscarPorDni(int dni) throws Exception;
	
}
