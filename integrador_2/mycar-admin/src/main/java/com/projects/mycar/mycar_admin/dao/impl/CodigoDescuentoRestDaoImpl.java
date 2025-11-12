package com.projects.mycar.mycar_admin.dao.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mycar.mycar_admin.domain.CodigoDescuentoDTO;
import com.projects.mycar.mycar_admin.dao.CodigoDescuentoRestDao;

@Repository
public class CodigoDescuentoRestDaoImpl extends BaseRestDaoImpl<CodigoDescuentoDTO, Long> implements CodigoDescuentoRestDao{

	public CodigoDescuentoRestDaoImpl() {
		super(CodigoDescuentoDTO.class, CodigoDescuentoDTO[].class, "http://localhost:9000/api/codigoDescuento");
	}

	@Override
	public CodigoDescuentoDTO buscarPorCodigo(String codigo) throws Exception {
		
		try {
			
			 String uri = UriComponentsBuilder
		                .fromUriString(baseUrl + "/searchByCodigo")
		                .queryParam("codigo", codigo)
		                .toUriString();
			 
			ResponseEntity<CodigoDescuentoDTO> response = this.restTemplate.getForEntity(uri, entityClass);
			CodigoDescuentoDTO codigoDescuento = response.getBody();
			
			return codigoDescuento;
			
		} catch (Exception e) {
			throw new Exception("Error al buscar el codigo de descuento", e);
		}
	}
	
	@Override
	public boolean existePorCodigo(String codigo) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/existsByCodigo")
	                .queryParam("codigo", codigo)
	                .toUriString();

	        ResponseEntity<Boolean> response = this.restTemplate.getForEntity(uri, Boolean.class);
	        Boolean existe = response.getBody();

	        return existe != null && existe; // prevenimos NullPointerException
	    } catch (Exception e) {
	        throw new Exception("Error al buscar el código de descuento", e);
	    }
	}


}
