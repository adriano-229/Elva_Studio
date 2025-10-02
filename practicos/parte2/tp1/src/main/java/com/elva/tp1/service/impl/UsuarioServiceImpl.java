package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.repository.UsuarioRepository;
import com.elva.tp1.service.UsuarioService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, Long> implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super(repository);
        this.usuarioRepository = repository;
    }

    @Override
    public Optional<Usuario> findByCuenta(String cuenta) {
        return usuarioRepository.findByCuenta(cuenta);
    }
}
