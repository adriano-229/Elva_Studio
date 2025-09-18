package elva.studio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.CuotaMensual;
import elva.studio.entities.Socio;
import elva.studio.repositories.CuotaMensualRepositorio;

@Service
public class CuotaMensualServicio {

	@Autowired
	private CuotaMensualRepositorio repoCuotaMensual;
	
	@Autowired
	private SocioServicio svcSocio;
	
	public double valorCuotaPorSocio(Socio socio) {
		
		// verifico que el socio exista, si existe tiene cuota asociada
		Optional<CuotaMensual> respuesta = repoCuotaMensual.findBySocio(socio);
		double valorCuota;
		if (respuesta.isPresent()) {
			CuotaMensual cuota = respuesta.get();
			valorCuota = cuota.getValorCuota().getValorCuota();
			return valorCuota;
		} else {
			valorCuota = 0;
		}
		
		return valorCuota;
		 
	}
}
