package com.projects.mycar.mycar_admin.service;

import java.util.List;

import com.example.mycar.mycar_admin.domain.FacturaDTO;

public interface FacturaService extends BaseService<FacturaDTO, Long>{
	
	List<FacturaDTO> obtenerPagosPendientes() throws Exception;
	FacturaDTO aprobarPago(Long idFactura) throws Exception;
	FacturaDTO anularPago(Long idFactura, String motivo) throws Exception;
	

}
