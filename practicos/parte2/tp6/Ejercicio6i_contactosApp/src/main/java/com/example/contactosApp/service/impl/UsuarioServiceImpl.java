package com.example.contactosApp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.ContactoDTO;
import com.example.contactosApp.domain.dto.PersonaDTO;
import com.example.contactosApp.domain.dto.UsuarioDTO;
import com.example.contactosApp.repository.BaseRepository;
import com.example.contactosApp.repository.UsuarioRepository;
import com.example.contactosApp.service.ContactoService;
import com.example.contactosApp.service.UsuarioService;
import com.example.contactosApp.service.mapper.BaseMapper;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, UsuarioDTO, Long> implements UsuarioService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PersonaServiceImpl personaService;
	
	
	public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository,
			BaseMapper<Usuario, UsuarioDTO> baseMapper) {
		super(baseRepository, baseMapper);
	}

	@Override
	public UsuarioDTO findByCuenta(String cuenta) throws Exception {
		
		return usuarioRepository.findByCuentaAndActivoTrue(cuenta)
				.map(baseMapper::toDto)
				.orElseThrow(() -> new Exception("No se encontró el usuario con la cuenta " + cuenta));
		
	}

	@Override
	public UsuarioDTO findByPersona(Long idPersona) throws Exception {
		
		return usuarioRepository.findByActivoTrueAndPersona_idAndPersona_ActivoTrue(idPersona)
				.map(baseMapper::toDto)
				.orElseThrow(() -> new Exception("No se encontró la persona con el id " + idPersona));
		
	}

	@Override
	protected Usuario updateEntityFromDto(Usuario usuario, UsuarioDTO usuarioDto) throws Exception {
		
		// Validar que no exista otro usuario con la misma cuenta
	    if (!usuario.getCuenta().equals(usuarioDto.getCuenta()) && existsByCuenta(usuarioDto.getCuenta())) {
	        throw new Exception("El nombre de usuario ya está en uso");
	    }

	    // Si la clave fue modificada, cifrarla antes de actualizar
	    if (usuarioDto.getClave() != null && !usuarioDto.getClave().isBlank()) {
	        // Evitamos volver a cifrar si el usuario no cambió la clave
	        if (!passwordEncoder.matches(usuarioDto.getClave(), usuario.getClave())) {
	        	usuarioDto.setClave(passwordEncoder.encode(usuarioDto.getClave()));
	        }
	    } else {
	        // Si el DTO no trae clave (por ejemplo, el usuario no cambió la contraseña),
	        // mantenemos la que ya tenía
	    	usuarioDto.setClave(usuario.getClave());
	    }
		
		//si estoy modificando el usuario no es necesario modificar a la persona en este caso
		usuario.setCuenta(usuarioDto.getCuenta());
		usuario.setClave(usuarioDto.getClave());
		
		return usuario;
	}

	@Override
	protected void validate(UsuarioDTO entityDto) throws Exception {
		if (entityDto.getCuenta() == null || entityDto.getCuenta().isEmpty()) {
			throw new Exception("ingrese un nombre de cuenta válido");
		}
		
		if (entityDto.getClave() == null || entityDto.getClave().isBlank()) {
			throw new Exception("ingrese una contraseña no puede ser vacia");
		}
		
	}

	@Override
	protected void beforeSave(UsuarioDTO entity) throws Exception {
		if (existsByCuenta(entity.getCuenta())) {
			throw new Exception("El nombre de usuario ya esta en uso");
		}
		
		//PersonaDTO persona = entity.getPersona();
		//personaService.save(persona);
		entity.setClave(passwordEncoder.encode(entity.getClave()));
	}

	@Override
	protected void afterSave(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeUpdate(UsuarioDTO entity) throws Exception {
		//si se actualizo la cuenta, verficar que no hayan dos cuentas con el mismo nombre
		//si se actualizo la clave, encriptar la clave
	}

	@Override
	protected void afterUpdate(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Usuario entity) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void afterDelete(Usuario entity) throws Exception {
		
		personaService.delete(entity.getPersona().getId());
		
	}

	@Override
	public boolean existsByCuenta(String cuenta) throws Exception {
		Optional<Usuario> opt = usuarioRepository.findByCuentaAndActivoTrue(cuenta);
		
		if (opt.isEmpty()) return false;
		
		return true;
	}

}
