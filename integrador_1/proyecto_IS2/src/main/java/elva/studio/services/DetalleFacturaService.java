package elva.studio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.DetalleFactura;
import elva.studio.entities.Factura;
import elva.studio.repositories.DetalleFacturaRepository;
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
	
	
	

}
