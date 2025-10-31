package com.example.biblioteca.dao.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;

import com.example.biblioteca.dao.DomicilioRestDao;
import com.example.biblioteca.domain.dto.DomicilioDTO;

@Repository
public class DomicilioRestDaoImpl extends BaseRestDaoImpl<DomicilioDTO, Long> implements DomicilioRestDao{

	public DomicilioRestDaoImpl() {
		super(DomicilioDTO.class, DomicilioDTO[].class, "http://localhost:9000/api/v1/domicilios");
	}

	@Override
	public DomicilioDTO buscarPorCalleNumeroYLocalidad(String calle, int numero, String denominacion) throws Exception {
		try {
			String uri = baseUrl + "/searchByCalleNumeroYLocalidad?calle=" + calle + "&numero=" + numero + "&denominacion=" + denominacion;
			ResponseEntity<DomicilioDTO> response = restTemplate.getForEntity(uri, entityClass);
			DomicilioDTO entity = response.getBody();
			
			return entity;
			
		} catch(HttpClientErrorException.NotFound ex) {
			return null;
		
		} catch (Exception e) {
			throw new Exception("Error al buscar domicilio por calle y numero", e);
		}
	}

}
