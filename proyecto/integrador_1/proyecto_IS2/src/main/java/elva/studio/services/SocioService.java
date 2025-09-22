package elva.studio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.Persona;
import elva.studio.entities.Socio;
import elva.studio.repositories.PersonaRepository;
import elva.studio.repositories.SocioRepositorio;
import jakarta.transaction.Transactional;

@Service
public class SocioService {
	
	@Autowired
	private SocioRepositorio repoSocio;
	
	@Autowired
	private PersonaRepository repoPersona;
	
	
	// crear socio
	@Transactional
	public void crearSocio(Long idPersona, Long numeroSocio) {
		// debo verificar que exista la persona, y le asigno nro de socio
		Optional<Persona> respuesta = repoPersona.buscarPorId(idPersona);
		
		if (respuesta.isPresent()) {
			// creo un socio
			Socio socio = new Socio();
			socio.setId(idPersona);
			socio.setNumeroSocio(numeroSocio);
			repoSocio.save(socio);
		}
	}
	// validar
	
	// modificar socio
	
	// listar socio
	@Transactional
	public List<Socio> listarSocios(){
		List<Socio> socios = repoSocio.findAll();
		return socios;
	}
	
	// listar socio activo
	@Transactional
	public List<Socio> listaSociosActivos(){
		List<Socio> sociosActivos = repoSocio.listaSociosActivos();
		return sociosActivos;
	}
	
	// asociar socio usuario
	
	// valido que un usuario es socio
	@Transactional
	public boolean validarSocio(Socio socio, Long numeroSocio) {
		if (socio.getNumeroSocio().equals(numeroSocio)) {
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
	
	@Transactional
	public Socio buscarPorId(Long idSocio) {
		Optional<Socio> respuesta = repoSocio.findById(idSocio);
		if (respuesta.isPresent()) {
			Socio socio = respuesta.get();
			return socio;
		}
		return null;
	}
}
