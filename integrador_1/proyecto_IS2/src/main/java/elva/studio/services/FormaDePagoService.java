package elva.studio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.FormaDePago;
import elva.studio.enumeration.TipoPago;
import elva.studio.repositories.FormaDePagoRepository;
import jakarta.transaction.Transactional;

@Service
public class FormaDePagoService {
	
	@Autowired
	private FormaDePagoRepository repoForma;
 
	@Transactional
	public FormaDePago buscarPorTipoPago(TipoPago tipoPago) {
		return repoForma.findByTipoPago(tipoPago);
	}
}
