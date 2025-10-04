package com.elva.tp1.repository;

import com.elva.tp1.domain.Usuario;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    List<Usuario> findAllByRolOrderByDocumento(Usuario.Rol rol);

}