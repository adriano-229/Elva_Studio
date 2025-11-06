package com.example.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.biblioteca.entities.Libro;

public interface LibroRepository extends BaseRepository<Libro, Long>{
	
	@Query(value = "SELECT l DISTINCT FROM Libro l " +
			"JOIN l.autores a " +
			"WHERE l.activo = true AND LOWER(a.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))" +
			"OR l.activo = true AND LOWER(a.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))" + 
			"OR l.activo = true AND LOWER(l.titulo) LIKE LOWER(CONCAT('%', :filtro, '%'))" +
			"OR l.activo = true AND LOWER(l.genero) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	List<Libro> buscar(@Param("filtro")String filtro);
	
	@Query(value = "SELECT l DISTINCT FROM Libro l " +
			"JOIN l.autores a " +
			"WHERE l.activo = true AND LOWER(a.nombre) LIKE LOWER(CONCAT('%', :filtro, '%'))" +
			"OR l.activo = true AND LOWER(a.apellido) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	List<Libro> buscarPorAutor(@Param("filtro")String filtro);
	
	@Query(value = "SELECT l FROM Libro l " +
			"WHERE l.activo = true AND LOWER(l.titulo) LIKE LOWER(CONCAT('%', :filtro, '%'))" +
			"OR l.activo = true AND LOWER(l.genero) LIKE LOWER(CONCAT('%', :filtro, '%'))")
	List<Libro> buscarPorTituloOGenero(@Param("filtro")String filtro);
	
	Optional<Libro> findByTituloIgnoreCaseAndActivoTrue(String titulo);
	
	boolean existsByAutores_Id(Long idAutor);

	List<Libro> findByTituloContainingIgnoreCaseAndActivoTrue(String valor);

	List<Libro> findByGeneroContainingIgnoreCaseAndActivoTrue(String valor);

	List<Libro> findByAutores_NombreContainingIgnoreCaseAndActivoTrue(String valor);

}
