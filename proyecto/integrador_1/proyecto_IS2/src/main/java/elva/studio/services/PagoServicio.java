package elva.studio.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.repositories.SocioRepositorio;

@Service
public class PagoServicio {
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	//permitir que si es socio pueda realizar un pago
	
	
}
