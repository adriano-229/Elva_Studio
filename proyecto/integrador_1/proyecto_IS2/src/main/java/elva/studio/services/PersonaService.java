package elva.studio.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import elva.studio.entities.Persona;
import elva.studio.entities.Socio;
import elva.studio.entities.Usuario;
import elva.studio.enumeration.Rol;
import elva.studio.enumeration.TipoDocumento;
import elva.studio.error.ErrorServicio;
import elva.studio.repositories.PersonaRepository;
import jakarta.transaction.Transactional;

@Service
public class PersonaService {
	
	@Autowired
	private PersonaRepository repoPersona;
	
	@Transactional
	public Persona buscarPersonaPorUsuario(Usuario usuario) {
		Long idUsuario = usuario.getId();
		Optional<Persona> respuesta = repoPersona.buscarPorIdUsuario(idUsuario);
		
		if (respuesta.isPresent()) {
			Persona persona = respuesta.get();
			return persona;

		}
		return null;
	}
	
	@Transactional
	public Persona buscarPersonaPorDocumento(String documento) {
		Optional<Persona> respuesta = repoPersona.buscarPorDocumento(documento);
		if (respuesta.isPresent()) {
			Persona persona = respuesta.get();
			return persona;
		}
		return null;
	}
	
	@Transactional
	public void crearPersona(String nombre, String apellido, Date fechaNacimiento,TipoDocumento tipoDoc, String documento,String telefono, String email) {
		// verifico que no exista
		Optional<Persona> respuesta = repoPersona.buscarPorDocumento(documento);
		
		if (!respuesta.isPresent()) {
			Persona persona = new Socio();
			persona.setNombre(nombre);
			persona.setApellido(apellido);
			persona.setFechaNacimiento(fechaNacimiento);
			persona.setNumeroDocumento(documento);
			persona.setTipoDocumento(tipoDoc);
			persona.setTelefono(telefono);
			persona.setCorreoElectronico(email);
			
			repoPersona.save(persona);
		}
	}
	
	// validar datos
	@Transactional
	public void validar(String nombre, String apellido) throws ErrorServicio {
		
		if (nombre.isEmpty() || nombre.equals(null)) {
			throw new ErrorServicio("El nombre no puede ser nulo ni vacio");
		}
		
	}
}
