package com.example.contactosApp.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.contactosApp.domain.Usuario;

@Repository
public interface UsuarioRepository extends BaseRepository<Usuario, Long>{
	
	Optional<Usuario> findByCuentaAndActivoTrue(String cuenta);
	
	Optional<Usuario> findByActivoTrueAndPersona_idAndPersona_ActivoTrue(Long idPersona) throws Exception;
	
}
