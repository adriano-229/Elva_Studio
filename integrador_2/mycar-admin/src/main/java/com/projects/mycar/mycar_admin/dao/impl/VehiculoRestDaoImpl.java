package com.projects.mycar.mycar_admin.dao.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.ImagenDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.VehiculoFormDTO;
import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;
import com.projects.mycar.mycar_admin.dao.VehiculoRestDao;

@Repository
public class VehiculoRestDaoImpl extends BaseRestDaoImpl<VehiculoDTO, Long> implements VehiculoRestDao{

	public VehiculoRestDaoImpl() {
		super(VehiculoDTO.class, VehiculoDTO[].class, "http://localhost:9000/api/v1/vehiculos");
	}

	@Override
	public List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta)
			throws Exception {
		
		try {
			
			String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByEstadoYPeriodo")
	                .queryParam("estado", estado)
	                .queryParam("desde", desde)
	                .queryParam("hasta", hasta)
	                .build()
	                .toUriString();
			
			ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
			
			VehiculoDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<VehiculoDTO>();
			}
			
			return Arrays.asList(array);
			
		} catch (Exception e) {
			throw new Exception("Error al buscar vehiculo por estado y periodo", e);
		}
	}

	@Override
	public List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception {

		try {
			
			String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByEstado")
	                .queryParam("estado", estado)
	                .build()
	                .toUriString();
			
			ResponseEntity<VehiculoDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
			
			VehiculoDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<VehiculoDTO>();
			}
			
			return Arrays.asList(array);
			
		} catch (Exception e) {
			throw new Exception("Error al buscar vehiculo por estado", e);
		}
		
	}
	
	public VehiculoDTO crearVehiculo(VehiculoDTO dto) throws Exception {
			
		try {
	        ResponseEntity<VehiculoDTO> response =
	                restTemplate.postForEntity(baseUrl, dto, VehiculoDTO.class);

	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
	            return response.getBody(); // Devuelve el VehiculoDTO con el ID del backend
	        } else {
	            throw new Exception("Error al crear el vehículo. Respuesta vacía o inválida.");
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al crear entidad", e);
	    }
			
	}

	public VehiculoDTO buscarPorPatente(String patente) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/searchByPatente")
	                .queryParam("patente", patente)
	                .build()
	                .toUriString();

	        ResponseEntity<VehiculoDTO> response = restTemplate.getForEntity(uri, entityClass);
	        return response.getBody();
	    } catch (HttpClientErrorException.NotFound e) {
	        return null;
	    } catch (Exception e) {
	        throw new Exception("Error al buscar vehículo por patente", e);
	    }
	}

	public CostoVehiculoDTO buscarCostoVehiculoPorId(Long id) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/costoVehiculo/{id}")
	                .buildAndExpand(id)
	                .toUriString();

	        ResponseEntity<CostoVehiculoDTO> response = restTemplate.getForEntity(uri, CostoVehiculoDTO.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            return response.getBody();
	        } else {
	            return null;
	        }
	    } catch (HttpClientErrorException.NotFound e) {
	        return null; // No se encontró
	    } catch (Exception e) {
	        throw new Exception("Error al buscar CostoVehiculo por ID " + id, e);
	    }
	}

	public ImagenDTO buscarImagenVehiculoPorId(Long id) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/imagenVehiculo/{id}")
	                .buildAndExpand(id)
	                .toUriString();

	        ResponseEntity<ImagenDTO> response = restTemplate.getForEntity(uri, ImagenDTO.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            return response.getBody();
	        } else {
	            return null;
	        }
	    } catch (HttpClientErrorException.NotFound e) {
	        return null;
	    } catch (Exception e) {
	        throw new Exception("Error al buscar ImagenVehiculo por ID " + id, e);
	    }
	}

	public CaracteristicaVehiculoDTO buscarCaracteristicaVehiculoPorId(Long id) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/caracteristicaVehiculo/{id}")
	                .buildAndExpand(id)
	                .toUriString();

	        ResponseEntity<CaracteristicaVehiculoDTO> response = restTemplate.getForEntity(uri, CaracteristicaVehiculoDTO.class);

	        if (response.getStatusCode().is2xxSuccessful()) {
	            return response.getBody();
	        } else {
	            return null;
	        }
	    } catch (HttpClientErrorException.NotFound e) {
	        return null;
	    } catch (Exception e) {
	        throw new Exception("Error al buscar CaracteristicaVehiculo por ID " + id, e);
	    }
	}
	


}
