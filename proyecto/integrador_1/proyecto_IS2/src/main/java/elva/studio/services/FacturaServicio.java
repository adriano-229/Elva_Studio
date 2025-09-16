package elva.studio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.repositories.FacturaRepositorio;

@Service
public class FacturaServicio {
	
	@Autowired
	private FacturaRepositorio repoFactura;
	
	//permitir que si pago que se genere la factura
	
	public String generarFactura() {
		return "";
	}
}
