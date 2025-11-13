package com.projects.mycar.mycar_cliente.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.projects.mycar.mycar_cliente.dao.VehiculoRestDao;
import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

@Repository
public class VehiculoRestDaoImpl extends BaseRestDaoImpl<VehiculoDTO, Long> implements VehiculoRestDao {

	public VehiculoRestDaoImpl() {
		super(VehiculoDTO.class, VehiculoDTO[].class, "http://localhost:9000/api/v1/vehiculos");
		
	}

	@Override
	public List<VehiculoDTO> findByEstado(EstadoVehiculo estado) throws Exception{
		
		try {

			String uri = baseUrl + "/searchByEstado?estado=" + estado;
			ResponseEntity<VehiculoDTO[]> response = this.restTemplate.getForEntity(uri, entityArrayClass);
			VehiculoDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<VehiculoDTO>();
			}
			
			return Arrays.asList(array);
			
			
		} catch (Exception e) {
			throw new Exception("Error al buscar persona por estado", e);
		}
		
	}
	/*
	public List<VehiculoDTO> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado){
		try {
	        String url = baseUrl + "/searchByEstado?estado=" + estado.name();
	        ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(url, VehiculoDTO[].class);
	        VehiculoDTO[] body = response.getBody();
	        return body != null ? Arrays.asList(body) : List.of();
	    } catch (Exception e) {
	        throw new RuntimeException("Error al buscar vehículos: " + e.getMessage(), e);
	    }
	}

	
	@Override
	public List<VehiculoDTO> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado) {
		String url = baseUrl + "/disponibles"; // ajustar este endpoint con el back
        return Arrays.asList(restTemplate.getForObject(url, VehiculoDTO[].class));
	}*/

}
