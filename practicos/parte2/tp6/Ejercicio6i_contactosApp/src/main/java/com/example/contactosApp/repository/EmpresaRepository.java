package com.example.contactosApp.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.contactosApp.domain.Empresa;

@Repository
public interface EmpresaRepository extends BaseRepository<Empresa, Long>{
	
	Optional<Empresa> findByNombreAndActivoTrue(String filtro) throws Exception;

}
