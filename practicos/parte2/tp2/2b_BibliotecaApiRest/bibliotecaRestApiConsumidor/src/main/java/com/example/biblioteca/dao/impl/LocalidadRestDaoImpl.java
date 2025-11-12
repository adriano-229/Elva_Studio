package com.example.biblioteca.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.dao.LocalidadRestDao;
import com.example.biblioteca.domain.dto.LocalidadDTO;

@Repository
public class LocalidadRestDaoImpl extends BaseRestDaoImpl<LocalidadDTO, Long> implements LocalidadRestDao{
	
	public LocalidadRestDaoImpl() {
		super(LocalidadDTO.class, LocalidadDTO[].class, "http://localhost:9000/api/v1/localidades");
		
	}

	@Override
	public List<LocalidadDTO> buscarPorDenominacion(String denominacion) throws Exception {
		
		try {
			
			String uri = baseUrl + "/searchByDenominacion?denominacion=" + denominacion;
			ResponseEntity<LocalidadDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
			LocalidadDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<LocalidadDTO>();
			}
			
			return Arrays.asList(array);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar localidad por denominacion", e);
		}
	}
}
