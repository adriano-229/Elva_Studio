package elva.studio.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.Socio;
import elva.studio.repositories.SocioRepositorio;
import jakarta.transaction.Transactional;

@Service
public class SocioServicio {
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	// valido que un usuario es socio
	@Transactional
	public boolean validarSocio(Socio socio, Long numeroSocio) {
		if (socio.getNumeroSocio() == numeroSocio) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public Socio buscarPorNrosocio(Long numeroSocio) {
		Optional<Socio> respuesta = repoSocio.buscarPorNroSocio(numeroSocio);
		if (respuesta.isPresent()){
			Socio socio = respuesta.get();
			return socio;
		}
		return null;
	}
}
