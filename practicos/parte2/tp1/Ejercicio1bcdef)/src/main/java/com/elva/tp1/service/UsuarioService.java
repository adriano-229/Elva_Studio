package com.elva.tp1.service;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService extends BaseService<Usuario, Long> {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
        this.usuarioRepository = repository;
    }

    public Optional<Usuario> findByCuenta(String cuenta) {
        return usuarioRepository.findByCuenta(cuenta);
    }


}