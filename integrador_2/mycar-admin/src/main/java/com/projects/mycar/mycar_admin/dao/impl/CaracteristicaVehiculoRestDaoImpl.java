package com.projects.mycar.mycar_admin.dao.impl;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.CaracteristicaVehiculoRestDao;

@Repository
public class CaracteristicaVehiculoRestDaoImpl extends BaseRestDaoImpl<CaracteristicaVehiculoDTO, Long> implements CaracteristicaVehiculoRestDao {

	public CaracteristicaVehiculoRestDaoImpl() {
		super(CaracteristicaVehiculoDTO.class, CaracteristicaVehiculoDTO[].class, "http://localhost:9000/api/v1/caracteristicaVehiculo");
	}

	public CaracteristicaVehiculoDTO crearCaracteristica(CaracteristicaVehiculoDTO dto) throws Exception {
		
		try {
	        ResponseEntity<CaracteristicaVehiculoDTO> response =
	                restTemplate.postForEntity(baseUrl, dto, CaracteristicaVehiculoDTO.class);

	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            return response.getBody(); 
	        } else {
	            throw new Exception("Error al crear el vehículo. Respuesta vacía o inválida.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al crear entidad", e);
	    }
			
	}
	
	public CaracteristicaVehiculoDTO actualizarCaracteristica(Long id, CaracteristicaVehiculoDTO dto) throws Exception {
	    try {
	        // URL del recurso a actualizar
	        String url = baseUrl + "/" + id;

	        // Se hace la petición PUT
	        restTemplate.put(url, dto);

	        ResponseEntity<CaracteristicaVehiculoDTO> response =
	                restTemplate.getForEntity(url, CaracteristicaVehiculoDTO.class);

	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            return response.getBody();
	        } else {
	            throw new Exception("Error al actualizar el vehículo. Respuesta vacía o inválida.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al actualizar entidad", e);
	    }
	}

	public Optional<CaracteristicaVehiculoDTO> buscarPorId(Long id) throws Exception {
	    String url = baseUrl + "/" + id;
	    ResponseEntity<CaracteristicaVehiculoDTO> response =
	            restTemplate.getForEntity(url, CaracteristicaVehiculoDTO.class);

	    if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	        return Optional.ofNullable(response.getBody());
	    } else {
	        throw new Exception("No se encontró la característica con id " + id);
	    }
	}

}
