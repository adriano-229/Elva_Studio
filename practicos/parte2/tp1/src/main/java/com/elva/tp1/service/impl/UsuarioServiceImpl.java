package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.repository.UsuarioRepository;
import com.elva.tp1.service.UsuarioService;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CrudServiceImpl<Usuario, Long> implements UsuarioService {

    public UsuarioServiceImpl(UsuarioRepository repository) {
        super(repository);
    }
}
