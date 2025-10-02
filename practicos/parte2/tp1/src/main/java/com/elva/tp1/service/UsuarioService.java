package com.elva.tp1.service;

import com.elva.tp1.domain.Usuario;
import java.util.Optional;

public interface UsuarioService extends CrudService<Usuario, Long> {
    Optional<Usuario> findByCuenta(String cuenta);
}
