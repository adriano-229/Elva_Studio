package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mycar.mycar_admin.domain.ClienteDTO;
import com.projects.mycar.mycar_admin.dao.ClienteRestDao;

@Repository
public class ClienteRestDaoImpl extends BaseRestDaoImpl<ClienteDTO, Long> implements ClienteRestDao{

	public ClienteRestDaoImpl() {
		super(ClienteDTO.class, ClienteDTO[].class, "http://localhost:9000/api/v1/cliente");
	}

	@Override
	public ClienteDTO buscarPorDni(String numeroDocumento) throws Exception {
		try {
			
			String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByDni")
	                .queryParam("numeroDocumento", numeroDocumento)
	                .build()
	                .toUriString();
			
			ResponseEntity<ClienteDTO> response = this.restTemplate.getForEntity(uri, entityClass);
			ClienteDTO cliente = response.getBody();
			
			return cliente;
			
		} catch (Exception e) {
			throw new Exception("Error al buscar el cliente por dni", e);
		}
			
		
	}

}
