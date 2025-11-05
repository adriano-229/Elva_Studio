package com.example.mycar.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;
import com.example.mycar.enums.RolUsuario;
import com.example.mycar.repositories.BaseRepository;
import com.example.mycar.repositories.UsuarioRepository;
import com.example.mycar.services.UsuarioService;
import com.example.mycar.services.mapper.BaseMapper;
import com.example.mycar.services.mapper.UsuarioMapper;

import jakarta.transaction.Transactional;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, UsuarioDTO, Long> implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository,
                              UsuarioMapper usuarioMapper,
                              PasswordEncoder passwordEncoder) {
        super(usuarioRepository, usuarioMapper);
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ---------------- MÉTODOS PERSONALIZADOS ----------------

    @Override
    @Transactional
    public void crearUsuario(String nombre, String clave, RolUsuario rol) {
        if (usuarioRepository.findByNombreUsuario(nombre).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombreUsuario(nombre);
        nuevo.setClave(passwordEncoder.encode(clave));
        nuevo.setRol(rol);
        nuevo.setActivo(true);

        usuarioRepository.save(nuevo);
    }

    @Override
    @Transactional
    public void modificarUsuario(Long id, String nombre, String clave, RolUsuario rol) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setNombreUsuario(nombre);
        usuario.setClave(passwordEncoder.encode(clave));
        usuario.setRol(rol);

        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public void validar(String nombreUsuario, String clave, RolUsuario rol) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        if (!usuario.getRol().equals(rol)) {
            throw new RuntimeException("El rol no coincide");
        }
    }

    @Override
    @Transactional
    public UsuarioDTO buscarUsuarioPorNombre(String nombre) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return baseMapper.toDto(usuario);
    }
    
    @Override
    @Transactional
    public void modificarClave(Long id, String claveActual, String claveNueva, String confirmarClave) {
        Usuario usuario = usuarioRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(claveActual, usuario.getClave())) {
            throw new RuntimeException("La clave actual no es correcta");
        }

        if (!claveNueva.equals(confirmarClave)) {
            throw new RuntimeException("Las claves nuevas no coinciden");
        }

        usuario.setClave(passwordEncoder.encode(claveNueva));
        usuarioRepository.save(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO login(String nombreUsuario, String clave) {
        Usuario usuario = usuarioRepository.findByNombreUsuario(nombreUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(clave, usuario.getClave())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return baseMapper.toDto(usuario);
    }

    // ---------------- MÉTODOS ABSTRACTOS DE BaseServiceImpl ----------------

    @Override
    protected Usuario updateEntityFromDto(Usuario entity, UsuarioDTO dto) throws Exception {
        entity.setNombreUsuario(dto.getNombreUsuario());
        entity.setRol(dto.getRol());
        // solo actualiza clave si viene informada
        if (dto.getClave() != null && !dto.getClave().isEmpty()) {
            entity.setClave(passwordEncoder.encode(dto.getClave()));
        }
        return entity;
    }

    @Override
    protected void validate(UsuarioDTO dto) throws Exception {
        if (dto.getNombreUsuario() == null || dto.getNombreUsuario().isEmpty()) {
            throw new Exception("El nombre de usuario es obligatorio");
        }
    }

    @Override
    protected void beforeSave(UsuarioDTO dto) throws Exception {
        if (usuarioRepository.findByNombreUsuario(dto.getNombreUsuario()).isPresent()) {
            throw new Exception("El nombre de usuario ya está registrado");
        }
    }

    @Override
    protected void afterSave(Usuario entity) throws Exception {
        // Podés registrar auditorías o logs aquí
    }

    @Override
    protected void afterUpdate(Usuario entity) throws Exception {
        // Igual que arriba
    }

    @Override
    protected void beforeDelete(Usuario entity) throws Exception {
        // Si necesitás validar algo antes de eliminar
    }

    @Override
    protected void afterDelete(Usuario entity) throws Exception {
        // Acciones post eliminación (auditoría, logs, etc.)
    }

	@Override
	public List<UsuarioDTO> findAllByIds(Iterable<Long> ids) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}

/*
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
}*/
