package com.projects.mycar.mycar_admin.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.example.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.example.mycar.mycar_admin.domain.CaracteristicaVehiculoDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.PagareDTO;
import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.projects.mycar.mycar_admin.dao.CostoRestDao;

@Repository
public class CostoRestDaoImpl extends BaseRestDaoImpl<CostoVehiculoDTO, Long> implements CostoRestDao{
	
	public CostoRestDaoImpl() {
		super(CostoVehiculoDTO.class, CostoVehiculoDTO[].class, "http://localhost:9000/api/costos");
		// TODO Auto-generated constructor stub
	}

	@Autowired
	private RestTemplate restTemplate;
	
	//private String baseUrl = "http://localhost:9000/api/costos";

	@Override
	public PagareDTO calcularPagare(List<Long> alquilerIds, Long clienteId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception {
		
		try {
			
			String uri = baseUrl + "/calcularCosto";

	        ResponseEntity<AlquilerFormDTO> response = restTemplate.postForEntity(
	                uri,
	                alquiler,          
	                AlquilerFormDTO.class   
	        );
			
	        return response.getBody();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al cotizar alquiler");
		}
		
	}
	
	public CostoVehiculoDTO crearCosto(CostoVehiculoDTO dto) throws Exception {
		
		try {
	        ResponseEntity<CostoVehiculoDTO> response =
	                restTemplate.postForEntity(baseUrl, dto, CostoVehiculoDTO.class);

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
	
	public CostoVehiculoDTO actualizarCosto(Long id, CostoVehiculoDTO dto) throws Exception {
			
		try {
	        // URL del recurso a actualizar
	        String url = baseUrl + "/" + id;

	        // Se hace la petición PUT
	        restTemplate.put(url, dto);

	        ResponseEntity<CostoVehiculoDTO> response =
	                restTemplate.getForEntity(url, CostoVehiculoDTO.class);

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

}
