package com.example.mycar.servicesImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.example.mycar.entities.Usuario;
import com.example.mycar.enums.RolUsuario;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.UsuarioRepository;
import com.example.mycar.services.UsuarioService;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService{

	private final UsuarioRepository repoUsuario;
	private final PasswordEncoder passwordEncoder; 

	@Autowired
	public UsuarioServiceImpl(UsuarioRepository repoUsuario,PasswordEncoder passwordEncoder) {
		super(repoUsuario);
		this.repoUsuario = repoUsuario;
		this.passwordEncoder = passwordEncoder;
		
	}

	@Override
	@Transactional
	public void crearUsuario(String nombre, String clave, RolUsuario rol) {
		// valido los datos
		validar(nombre,clave,rol);
		
		Usuario usuario = new Usuario();
		usuario.setNombreUsuario(nombre);
		
		String claveEncriptada = passwordEncoder.encode(clave);
		usuario.setClave(claveEncriptada);
		
		usuario.setRol(rol);

		repoUsuario.save(usuario);
		
	}

	@Override
	@Transactional
	public void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol) {
		// verifico que exista
		Optional<Usuario> respuesta = repoUsuario.findById(id);
		if (respuesta.isPresent()) {
			// existe, lo moifico
			Usuario usuario = respuesta.get();
			validar(nombre,clave,rol);
			
			usuario.setNombreUsuario(nombre);
			
			if (clave != null && !clave.trim().isEmpty()) {
		        usuario.setClave(passwordEncoder.encode(clave));
		    }
			usuario.setRol(rol);
			
			repoUsuario.save(usuario);
		}
		
	}

	@Override
	@Transactional
	public void validar(String nombreUsuario, String clave, RolUsuario rol) {
		
		Optional<Usuario> usuarioExistente = repoUsuario.findByNombreUsuario(nombreUsuario);
		if (nombreUsuario.isEmpty() || nombreUsuario == null || usuarioExistente.isPresent()) {
			throw new IllegalArgumentException("Nombre usuario incorrecto o duplicado");
		}
		if (clave == null || clave.length() < 8) {
			throw new IllegalArgumentException("La clave no puede ser vacia o menor a 8 digitos");
		}
		if (rol == null) {
			throw new IllegalArgumentException("Debe asignarle un rol al usuario");
		}
		
	}

	@Override
	@Transactional
	public Usuario buscarUsuarioPorNombre(String nombre) {
		Optional<Usuario> respuesta = repoUsuario.findByNombreUsuario(nombre);
		if (respuesta.isPresent()) {
			Usuario usuario = respuesta.get();
			return usuario;
		}
		return null;
	}

	@Override
	@Transactional
	public void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave) {
		
		Optional<Usuario> respuesta = repoUsuario.findById(id);
		if (respuesta.isPresent()) {
			// verifico clave actual
			Usuario usuario = respuesta.get();
			
			if (!passwordEncoder.matches(claveActual, usuario.getClave())) {
				throw new IllegalArgumentException("La clave actual es incorrecta");
			}
			
			if (!claveNueva.equals(confirmarClave)) {
				throw new IllegalArgumentException("Error al ingresar la nueva clave");
			}
			
			String claveEncriptada = passwordEncoder.encode(claveNueva);
			usuario.setClave(claveEncriptada);
			repoUsuario.save(usuario);
		}
		
	}

	@Override
	public Usuario login(String nombreUsuario, String clave) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario save(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario update(Long id, Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
