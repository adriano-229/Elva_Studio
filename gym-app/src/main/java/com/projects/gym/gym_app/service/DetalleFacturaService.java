package com.projects.gym.gym_app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projects.gym.gym_app.domain.DetalleFactura;
import com.projects.gym.gym_app.domain.Factura;
import com.projects.gym.gym_app.repository.DetalleFacturaRepository;
import jakarta.transaction.Transactional;

@Service
public class DetalleFacturaService {
	
	@Autowired
	private DetalleFacturaRepository repoDetalle;
	
	
	// guardar el detalle de una factura
	@Transactional
	public void guardarDetalles(Factura factura, List<DetalleFactura> detalles) {
		for (DetalleFactura detalle : detalles) {
			detalle.setFactura(factura);
			repoDetalle.save(detalle);
		}
	}
	
	public List<DetalleFactura> buscarPorCuota(String idCuota){
		return repoDetalle.buscarPorCuota(idCuota);
	}
	
	

}
