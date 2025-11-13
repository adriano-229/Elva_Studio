package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}