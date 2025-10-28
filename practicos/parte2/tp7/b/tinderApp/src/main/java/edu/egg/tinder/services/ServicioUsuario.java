package edu.egg.tinder.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.egg.tinder.entities.Foto;
import edu.egg.tinder.entities.Usuario;
import edu.egg.tinder.entities.Zona;
import edu.egg.tinder.error.ErrorServicio;
import edu.egg.tinder.repository.RepositorioUsuario;
import edu.egg.tinder.repository.RepositorioZona;
import jakarta.transaction.Transactional;

@Service
public class ServicioUsuario implements UserDetailsService {

	// llamada al repositorio
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Autowired
	private ServicioNotificacion servicioNotificacion;
	
	@Autowired
	private ServicioFoto servicioFoto; 
	
	@Autowired
	private RepositorioZona repoZona;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//acciones
	

	//permitir que el usuario se registre
	@Transactional
	public void registrar(MultipartFile archivo, String nombre, String apellido, String mail, String clave1, String clave2, long idZona) throws ErrorServicio {
		
		Zona zona = repoZona.getById(idZona);
		//verificaciones
		verificar(nombre, apellido, mail, clave1, clave2, zona);
		
		
		Usuario usuario = new Usuario();
		usuario.setNombre(nombre);
		usuario.setApellido(apellido);
		usuario.setMail(mail);
		usuario.setClave(passwordEncoder.encode(clave1));
		usuario.setAlta(new Date());
		usuario.setZona(zona);
		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setCredentialsNonExpired(true);
		usuario.setEnabled(true);
		
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
			usuario.setClave(passwordEncoder.encode(clave1));
			usuario.setZona(zona);
			usuario.setAccountNonExpired(true);
			usuario.setAccountNonLocked(true);
			usuario.setCredentialsNonExpired(true);
			
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
			usuario.setEnabled(false);
			usuario.setAccountNonLocked(false);
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
			usuario.setEnabled(true);
			usuario.setAccountNonExpired(true);
			usuario.setAccountNonLocked(true);
			usuario.setCredentialsNonExpired(true);
			repositorioUsuario.save(usuario);
		} else {
			throw new ErrorServicio("El usuario no fue encontrado");
		}	
	}
	
	public Usuario buscarPorId(long id) {
		
		Optional<Usuario> respuesta = repositorioUsuario.findById(id);
		
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			return usuario;
		}
		return null;	
	}

	@Transactional
	public Usuario buscarPorMail(String mail) {
		Optional<Usuario> respuesta = repositorioUsuario.buscarPorMail(mail);
		if (respuesta.isPresent()) {
			return respuesta.get();
		}
		throw new UsernameNotFoundException("No se encontró el usuario con mail " + mail);
	}

	@Transactional
	public Usuario actualizarDatosBasicos(String mail, String nombre, String apellido, String nuevoMail, Long idZona, String clave) throws ErrorServicio {
		Usuario usuario = buscarPorMail(mail);

		if (StringUtils.hasText(nombre)) {
			usuario.setNombre(nombre);
		}

		if (StringUtils.hasText(apellido)) {
			usuario.setApellido(apellido);
		}

		if (StringUtils.hasText(nuevoMail)) {
			usuario.setMail(nuevoMail);
		}

		if (idZona != null) {
			Zona zona = repoZona.findById(idZona).orElseThrow(() -> new ErrorServicio("La zona indicada no existe"));
			usuario.setZona(zona);
		}

		if (StringUtils.hasText(clave)) {
			if (clave.length() < 6) {
				throw new ErrorServicio("La clave no puede ser menor a 6 dígitos");
			}
			usuario.setClave(passwordEncoder.encode(clave));
			usuario.setCredentialsNonExpired(true);
		}

		usuario.setAccountNonExpired(true);
		usuario.setAccountNonLocked(true);
		usuario.setEnabled(true);

		return repositorioUsuario.save(usuario);
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
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
		Usuario usuario = buscarPorMail(mail);

		boolean activo = usuario.getBaja() == null;
		boolean habilitado = usuario.isEnabled() && activo;

		List<SimpleGrantedAuthority> permisos = List.of(new SimpleGrantedAuthority("ROLE_USUARIO"));

		return User
				.withUsername(usuario.getMail())
				.password(usuario.getClave())
				.authorities(permisos)
				.accountExpired(!usuario.isAccountNonExpired())
				.accountLocked(!usuario.isAccountNonLocked())
				.credentialsExpired(!usuario.isCredentialsNonExpired())
				.disabled(!habilitado)
				.build();
	}
	
	
}
