package com.elva.tp1.service;

import com.elva.tp1.domain.Usuario;
import com.elva.tp1.repository.UsuarioRepository;

public class UsuarioService extends BaseService<Usuario, Long> {

    public UsuarioService(UsuarioRepository repository) {
        super(repository);
    }


}