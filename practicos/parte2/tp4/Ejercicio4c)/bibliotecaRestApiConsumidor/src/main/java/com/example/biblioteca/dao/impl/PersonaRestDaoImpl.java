package com.example.biblioteca.dao.impl;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.dao.PersonaRestDao;
import com.example.biblioteca.domain.dto.PersonaDTO;

@Repository
public class PersonaRestDaoImpl extends BaseRestDaoImpl<PersonaDTO, Long> implements PersonaRestDao{

	public PersonaRestDaoImpl() {
		super(PersonaDTO.class, PersonaDTO[].class, "http://localhost:9000/api/v1/personas");
		
	}

	@Override
	public List<PersonaDTO> buscarPorNombreOApellido(String filtro) throws Exception {
		
		try {
			String uri = baseUrl + "/search?filtro=" + filtro;
			ResponseEntity<PersonaDTO[]> response = this.restTemplate.getForEntity(uri, entityArrayClass);
			PersonaDTO[] array = response.getBody();
			
			return Arrays.asList(array);
			
			
		} catch (Exception e) {
			throw new Exception("Error al buscar persona por nombre y apellido", e);
		}
	}
	
	@Override
	public PersonaDTO buscarPorDni(int dni) throws Exception {
		
		try {
			String uri = baseUrl + "/searchByDni?dni=" + dni;
			ResponseEntity<PersonaDTO> response = this.restTemplate.getForEntity(uri, entityClass);
			PersonaDTO persona = response.getBody();
			
			return persona;
			
			
		} catch (Exception e) {
			throw new Exception("Error al buscar persona por nombre y apellido", e);
		}
	}

}
