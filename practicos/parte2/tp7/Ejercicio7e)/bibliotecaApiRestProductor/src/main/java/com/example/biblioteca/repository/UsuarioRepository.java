package com.example.biblioteca.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.biblioteca.entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long>{

	Optional<Usuario> findByNombreUsuario(String nombreUsuario);

}
