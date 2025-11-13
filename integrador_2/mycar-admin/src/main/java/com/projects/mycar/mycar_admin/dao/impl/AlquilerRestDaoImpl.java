package com.projects.mycar.mycar_admin.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mycar.mycar_admin.domain.AlquilerDTO;
import com.projects.mycar.mycar_admin.dao.AlquilerRestDao;

@Repository
public class AlquilerRestDaoImpl extends BaseRestDaoImpl<AlquilerDTO, Long> implements AlquilerRestDao{

	public AlquilerRestDaoImpl() {
		super(AlquilerDTO.class, AlquilerDTO[].class, "http://localhost:9000/api/v1/alquileres");
	}
	
	@Override
	public List<AlquilerDTO> buscarPorPeriodo(LocalDate desde, LocalDate hasta) throws Exception {
		try {
			
			String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByPeriodo")
	                .queryParam("desde", desde)
	                .queryParam("hasta", hasta)
	                .build()
	                .toUriString();

	        ResponseEntity<AlquilerDTO[]> response =
	                restTemplate.getForEntity(uri, AlquilerDTO[].class);
			
			AlquilerDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<AlquilerDTO>();
			}
			
			return Arrays.asList(array);
			
		} catch (Exception e) {
			throw new Exception("Error al buscar alquiler por periodo",e);
		}
		
	}

	@Override
	public List<AlquilerDTO> buscarPorCliente(Long idCliente) throws Exception{
		try {
			
			String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByCliente")
	                .queryParam("clienteId", idCliente)
	                .build()
	                .toUriString();

	        ResponseEntity<AlquilerDTO[]> response =
	                restTemplate.getForEntity(uri, AlquilerDTO[].class);
			
			AlquilerDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<AlquilerDTO>();
			}
			
			return Arrays.asList(array);
			
		} catch (Exception e) {
			throw new Exception("Error al buscar alquiler por cliente",e);
		}
	}

}
