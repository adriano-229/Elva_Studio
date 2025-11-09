package com.example.mycar.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.mycar.entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long>{

	Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}