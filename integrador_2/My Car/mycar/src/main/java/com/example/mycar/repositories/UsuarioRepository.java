package com.example.mycar.repositories;

import com.example.mycar.entities.Usuario;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}