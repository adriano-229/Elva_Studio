package com.example.demo.repositories;

import org.springframework.stereotype.Repository;

import com.example.demo.entities.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long> {

}
