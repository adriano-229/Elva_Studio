package com.example.contactosApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.contactosApp.domain.Persona;

@Repository
public interface PersonaRepository extends BaseRepository<Persona, Long>{
	
	@Query(value = "SELECT p DISTINCT FROM Persona p " +
			"WHERE p.activo = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))" +
			"OR p.activo = true AND LOWER(p.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	List<Persona> findByNombre(@Param("filtro")String filtro);

}
