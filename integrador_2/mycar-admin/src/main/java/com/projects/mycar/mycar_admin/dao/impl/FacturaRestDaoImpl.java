package com.projects.mycar.mycar_admin.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.mycar.mycar_admin.domain.FacturaDTO;
import com.projects.mycar.mycar_admin.dao.FacturaRestDao;

@Repository
public class FacturaRestDaoImpl extends BaseRestDaoImpl<FacturaDTO, Long> implements FacturaRestDao{

	public FacturaRestDaoImpl() {
		super(FacturaDTO.class, FacturaDTO[].class, "http://localhost:9000/api/pagos");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<FacturaDTO> obtenerPagosPendientes() throws Exception {
		try {
			
			String uri = baseUrl;
			ResponseEntity<FacturaDTO[]> response = this.restTemplate.getForEntity(uri, entityArrayClass);
			FacturaDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<FacturaDTO>();
			}
			
			return Arrays.asList(array);
			
		} catch (Exception e) {
			throw new Exception("Error al obtener pagos pendientes",e);
		}
		
		
	}

	@Override
	public FacturaDTO aprobarPago(Long idFactura) throws Exception {
		
		try {
			
			String uri = baseUrl + "/aprobar/{facturaId}";
			ResponseEntity<FacturaDTO> response = this.restTemplate.getForEntity(uri, entityClass, idFactura);
			FacturaDTO factura = response.getBody();
			
			return factura;
			
		} catch (Exception e) {
			throw new Exception("Error al aprobar el pago", e);
		}
	}

	@Override
	public FacturaDTO anularPago(Long idFactura, String motivo) throws Exception {
	    try {
	        String uri = UriComponentsBuilder
	                .fromUriString(baseUrl + "/anular/{facturaId}")
	                .queryParam("motivo", motivo)
	                .buildAndExpand(idFactura)
	                .toUriString();

	        ResponseEntity<FacturaDTO> response =
	                restTemplate.getForEntity(uri, FacturaDTO.class);

	        return response.getBody();

	    } catch (Exception e) {
	        throw new Exception("Error al anular el pago", e);
	    }
	}


}
