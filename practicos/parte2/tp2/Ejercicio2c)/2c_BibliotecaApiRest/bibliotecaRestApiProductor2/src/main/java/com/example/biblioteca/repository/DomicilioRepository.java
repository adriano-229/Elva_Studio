package com.example.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.biblioteca.entities.Domicilio;

public interface DomicilioRepository extends BaseRepository<Domicilio, Long>{
	
	@Query("SELECT d FROM Domicilio d " +
		       "WHERE d.activo = true " +
		       "AND LOWER(d.calle) = LOWER(:calle) " +
		       "AND d.numero = :numero " +
		       "AND LOWER(d.localidad.denominacion) = LOWER(:denominacion)")
		Optional<Domicilio> findByCalleNumeroYLocalidadDenominacion(
		        @Param("calle") String calle,
		        @Param("numero") int numero,
		        @Param("denominacion") String denominacion);
	
	boolean existsByLocalidad_Id(Long idLocalidad);

}
