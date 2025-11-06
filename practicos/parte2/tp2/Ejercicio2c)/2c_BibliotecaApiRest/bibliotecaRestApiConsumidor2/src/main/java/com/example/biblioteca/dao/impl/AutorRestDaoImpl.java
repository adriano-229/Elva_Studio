package com.example.biblioteca.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.dao.AutorRestDao;
import com.example.biblioteca.domain.dto.AutorDTO;

@Repository
public class AutorRestDaoImpl extends BaseRestDaoImpl<AutorDTO, Long> implements AutorRestDao{
	
	public AutorRestDaoImpl() {
		super(AutorDTO.class, AutorDTO[].class, "http://localhost:9000/api/v1/autores");
		
	}
	
	@Override
	public List<AutorDTO> buscarPorNombreApellido(String filtro) throws Exception{
		
		try {
			String uri = baseUrl + "/searchByFiltro?filtro=" + filtro;
			ResponseEntity<AutorDTO[]> response = this.restTemplate.getForEntity(uri, entityArrayClass);
			AutorDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<AutorDTO>();
			}
			
			return Arrays.asList(array);
			
			
		} catch (Exception e) {
			throw new Exception("Error al buscar persona por nombre y apellido", e);
		}
		
	}

}
