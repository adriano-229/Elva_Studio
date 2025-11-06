package com.example.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.entities.Localidad;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long>{
	
	@Query(value = "SELECT l FROM Localidad l WHERE LOWER(l.denominacion) LIKE LOWER(CONCAT('%', :filtro, '%')) AND l.activo = true")
	List<Localidad> findByFiltro(String filtro);
	
	Optional<Localidad> findByDenominacionAndActivoTrueIgnoreCase(String denominacion);

}
