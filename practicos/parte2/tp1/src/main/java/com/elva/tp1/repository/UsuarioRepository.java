package com.elva.tp1.repository;

import com.elva.tp1.domain.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    List<Usuario> findAllByRolOrderByEmail(Usuario.Rol rol);

    Optional<Usuario> findByCuenta(String cuenta);
}