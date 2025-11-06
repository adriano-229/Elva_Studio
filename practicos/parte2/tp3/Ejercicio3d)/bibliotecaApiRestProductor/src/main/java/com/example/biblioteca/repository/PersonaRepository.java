package com.example.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.entities.Persona;

@Repository
public interface PersonaRepository extends BaseRepository<Persona, Long>{
	
	// metodo de query oara obtener la persona ya sea que se ingresa el nombre o apellido
	List<Persona> findByActivoTrueAndNombreContainingOrActivoTrueAndApellidoContaining(String nombre, String apellido);
	
	Page<Persona> findByActivoTrueAndNombreContainingOrActivoTrueAndApellidoContaining(String nombre, String apellido, Pageable pageable);
	
	//@Query(value = "SELECT p FROM Persona p WHERE p.nombre LIKE %?1%")
	//List<Persona> search(String filtro);
	
	@Query(value = "SELECT p FROM Persona p WHERE p.activo = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR p.activo = true AND LOWER(p.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	List<Persona> search(@Param("filtro")String filtro);
	
	@Query(value = "SELECT p FROM Persona p WHERE p.activo = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR p.activo = true AND LOWER(p.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	Page<Persona> search(@Param("filtro")String filtro, Pageable pageable);
	
	@Query(
			value = "SELECT * FROM persona WHERE persona.nombre LIKE CONCAT('%', :filtro, '%') OR persona.apellido LIKE CONCAT('%', :filtro, '%')",
			nativeQuery = true
	)
	List<Persona> searchNativo(@Param("filtro")String filtro);
	
	@Query(
			value = "SELECT * FROM persona WHERE persona.nombre LIKE CONCAT('%', :filtro, '%') OR persona.apellido LIKE CONCAT('%', :filtro, '%')",
			// Creamos una query de conteo que nos permita contar la cantidad de paginas
			countQuery = "SELECT count(*) FROM persona",
			nativeQuery = true
	)
	Page<Persona> searchNativo(@Param("filtro")String filtro, Pageable pageable);
	
	boolean existsByLibros_Id(Long idLibro);
	
	Optional<Persona> findByDniAndActivoTrue(int dni);
	
	
	
	
	
	// este metodo permite verificar que ya existe en la base de datos una persona con es numero de dni
	//boolean existsByDni(int dni);

}
