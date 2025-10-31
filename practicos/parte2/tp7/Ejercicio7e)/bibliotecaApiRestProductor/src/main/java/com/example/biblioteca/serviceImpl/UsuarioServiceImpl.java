package com.example.biblioteca.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Usuario;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.UsuarioRepository;
import com.example.biblioteca.service.UsuarioService;

@Service
public class UsuarioServiceImpl extends BaseServiceImpl<Usuario, Long> implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public UsuarioServiceImpl(BaseRepository<Usuario, Long> baseRepository) {
		super(baseRepository);
	}

	@Override
	public Usuario save(Usuario entity) throws Exception {
		if (repository.findByNombreUsuario(entity.getNombreUsuario()).isPresent()) {
            throw new Exception("El nombre de usuario ya existe");
        }
        // encriptar contraseña antes de guardar
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return repository.save(entity);
	}

	@Override
	public Usuario update(Long id, Usuario entity) throws Exception {
		Usuario usuarioExistente = repository.findById(id)
                .orElseThrow(() -> new Exception("Usuario no encontrado"));
        usuarioExistente.setNombreUsuario(entity.getNombreUsuario());
        usuarioExistente.setRol(entity.getRol());
        if (entity.getPassword() != null && !entity.getPassword().isEmpty()) {
            usuarioExistente.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        return repository.save(usuarioExistente);
	}

	@Override
	public boolean delete(Long id) throws Exception {
		if (!repository.existsById(id)) {
            throw new Exception("Usuario no encontrado");
        }
        repository.deleteById(id);
        return true;
	}
}
