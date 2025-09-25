package elva.studio.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import elva.studio.ProyectoIs2Application;
import elva.studio.entities.Persona;
import elva.studio.entities.Usuario;
import elva.studio.enumeration.Rol;
import elva.studio.error.ErrorServicio;
import elva.studio.repositories.PersonaRepository;
import elva.studio.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repoUsuario;
	
	private PersonaRepository repoPersona;

	// crear usuario
	@Transactional
	public void crearUsuario(Long idPersona, String nombre, String clave1, String clave2, Rol rol) {
		// verifico que sea persona
		Optional<Persona> respuesta = repoPersona.findById(idPersona); 
		if (respuesta.isPresent()) {
			try {
				validar(nombre,clave1,clave2, rol);
				Usuario usuario =new Usuario();
				usuario.setNombreUsuario(nombre);
				usuario.setClave(clave1);
				usuario.setRol(rol);
				
				repoUsuario.save(usuario);
				
			} catch (ErrorServicio e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	@Transactional
	public boolean validarUsuarioLogueado(Usuario usuario, String nombreUsuario, String clave) {
		if (usuario.getNombreUsuario().equals(nombreUsuario) && usuario.getClave().equals(clave)) {
			return true;
		}
		return false;
		
	}
	
	// modificar usuario
	@Transactional
	public void modificarUsuario(Long idUsuario, String nombre, String clave, Rol rol) throws ErrorServicio{
		// validar datos
		Usuario usuario = buscarUsuarioPorId(idUsuario);
		try {
			validar(nombre, clave,clave, rol);
			if (usuario != null) {
				usuario.setNombreUsuario(nombre);
				usuario.setClave(clave);
				usuario.setRol(rol);
			} else { 
				throw new ErrorServicio("Usuario no encontrado");
			}
					
		} catch (ErrorServicio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Transactional
	public Usuario buscarUsuarioPorId(Long id) {
		Optional<Usuario> respuesta = repoUsuario.findById(id);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			return usuario;
		}
		return null;
	}
	
	@Transactional
	public Usuario buscarUsuarioPorNombre(String nombreUsuario) {
		Optional<Usuario> respuesta = repoUsuario.buscarPorNombre(nombreUsuario);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			return usuario;
		}
		return null;
	}
	
	@Transactional
	public void validar(String nombre, String clave1, String clave2, Rol rol) throws ErrorServicio {
		
		if (nombre.isEmpty() || nombre.equals(null)) {
			throw new ErrorServicio("El nombre no puede ser nulo ni vacio");
		}
		if (clave1 == null || clave1.length() < 8) {
			throw new ErrorServicio("La clave no debe ser vacia no menor a 8 digitos");
		}
		if (clave2 == null || clave2.length() < 8) {
			throw new ErrorServicio("La clave no debe ser vacia no menor a 8 digitos");
		}
		if (!clave1.equals(clave2)) {
			throw new ErrorServicio("Contraseñas no coinciden");
		}
		if (!(rol.equals("Admin") || rol.equals("Socio") || rol.equals("Empleado"))){
			throw new ErrorServicio("Rol de usuario incorrecto");
		}
	}
	
	
	// listar usuario por rol
	@Transactional
	public List<Usuario> listarUsuariosPorRol(Rol rol){
		List<Usuario> lista = repoUsuario.buscarPorRol(rol);
		return lista;
	}
	
	// dado el id devolver el rol del usuario
	@Transactional
	public Rol rolUsuario(Long id) {
		Usuario usuario = buscarUsuarioPorId(id);
		if (usuario != null) {
			Rol rolUsuario = usuario.getRol();
			return rolUsuario;
		}
		return null;
	}

}
