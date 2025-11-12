package com.example.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.entities.Autor;

@Repository
public interface AutorRepository extends BaseRepository<Autor, Long>{
	
	@Query(value = "SELECT a FROM Autor a WHERE a.activo = true AND LOWER(a.nombre) LIKE LOWER(CONCAT('%', :filtro, '%')) OR LOWER(a.apellido) LIKE LOWER(CONCAT('%', :filtro, '%')) AND a.activo = true")
	List<Autor> findByFiltro(@Param("filtro")String filtro);
	 

}
