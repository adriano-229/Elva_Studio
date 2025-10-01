package edu.egg.tinder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import edu.egg.tinder.entities.Mascota;

@Repository
public interface RepositorioMascota extends JpaRepository<Mascota,Long> {
	
	@Query("SELECT m FROM Mascota m WHERE m.usuario.id = :id AND m.baja IS NULL")
	public List<Mascota> buscarMascotaPorUsuario(@Param("id") Long id);

}
