package edu.egg.tinder.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioUsuario;
import edu.egg.tinder.repository.RepositorioZona;
import jakarta.transaction.Transactional;

@Service
public class ServicioUsuario {

	// llamada al repositorio
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private ServicioFoto servicioFoto; 
	
	@Autowired
	private RepositorioZona repoZona;
	
	//acciones
	

	//permitir que el usuario se registre
	@Transactional
	public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave1, String clave2, Long idZona) throws ErrorServicio {
		
		Zona zona = repoZona.getById(idZona);
		//verificaciones
		verificar(nombre, apellido, mail, clave1, clave2, zona);
		
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setMail(mail);
		usuario.setClave(clave1);
		usuario.setAlta(new Date());
		usuario.setZona(zona);
		
		Foto foto = servicioFoto.guardar(archivo);
		usuario.setFoto(foto);
		
		repositorioUsuario.save(usuario);
		
		

		//le mando al usuario un mail para avisarle que se registro
		//servicioNotificacion.enviar("Bienvenido al Tinder de Mascotas", "Tinder de Mascota",usuario.getMail());
	}
	
	
	//permitir que si esta registrado pueda editar
	@Transactional
	public void editar(MultipartFile archivo, long usuarioId, String nombre, String apellido, String mail, String clave1, String clave2,long idZona) throws ErrorServicio {
		
		Zona zona = repoZona.getById(idZona);
		
		verificar(nombre, apellido, mail, clave1, clave2, zona);
		
		Optional<Usuario> respuesta = repositorioUsuario.findById(usuarioId); 
		
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			usuario.setApellido(apellido);
			usuario.setMail(mail);
			usuario.setNombre(nombre);
			usuario.setClave(clave1);
			
			Long fotoId = null; 
			if (usuario.getFoto() != null) {
				fotoId = usuario.getFoto().getId();
			}
			
			try {
				Foto foto = servicioFoto.actualizar(fotoId, archivo);
				usuario.setFoto(foto);
				
				repositorioUsuario.save(usuario);
				
			} catch (Exception e) {
				throw new ErrorServicio("No se pudo actualizar la foto");
			}		
		} else {
			throw new ErrorServicio("No se encontro el usuario");
		}
	}
	
	//permitir que si esta registrado pueda deshabilitarse
	@Transactional
	public void deshabilitar(Long id) throws ErrorServicio{
		
		Optional<Usuario> respuesta = repositorioUsuario.findById(id);
		
		if (respuesta.isPresent()) {
			// logica para deshabilitarlo
			Usuario usuario = respuesta.get();
			usuario.setBaja(new Date());
			repositorioUsuario.save(usuario);
		} else {
			throw new ErrorServicio("El usuario no fue encontrado");
		}
		
	}
	
	//permitir que pueda volver a habilitarse
	@Transactional
	public void habilitar(Long id) throws ErrorServicio{
			
		Optional<Usuario> respuesta = repositorioUsuario.findById(id);
		
		if (respuesta.isPresent()) {
			// logica para deshabilitarlo
			Usuario usuario = respuesta.get();
			usuario.setAlta(new Date());
			repositorioUsuario.save(usuario);
		} else {
			throw new ErrorServicio("El usuario no fue encontrado");
		}	
	}
	
	// dado un mail verifico que sea la contraseña
	@Transactional
	public boolean validarLogin(Usuario usuario, String email, String clave) {
		
		if (usuario.getMail().equals(email) && usuario.getClave().equals(clave)) {
			return true;
		}
		return false;
	}

	
	//verificaciones
	@Transactional
	public void verificar(String nombre, String apellido, String mail, String clave1, String clave2,Zona zona) throws ErrorServicio{
		
		if (nombre == null || nombre.isEmpty()) {
			throw new ErrorServicio("EL nombre no puede ser nulo");
		}
		if (apellido == null || apellido.isEmpty()) {
			throw new ErrorServicio("El apellido no puede ser nulo");
		}
		if (mail == null || mail.isEmpty()) {
			throw new ErrorServicio("El mail no puede ser nulo");
		}
		if (clave1 == null || clave1.isEmpty() || clave1.length()<6) {
			throw new ErrorServicio("La clave no puede ser nula ni menor a 6 digitos");
		}
		if (!clave1.equals(clave2)) {
			throw new ErrorServicio("Las claves deben ser iguales");
		}
		if (zona == null) {
			throw new ErrorServicio("La zona no pude esatr vacio");
		}
			
	}
	
	
	
}
