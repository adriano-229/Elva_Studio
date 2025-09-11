package edu.egg.tinder.repository;

import edu.egg.tinder.entities.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioMascota extends JpaRepository<Mascota, Long> {

    @Query("SELECT m FROM Usuario u JOIN u.mascotas m WHERE u.id = :id")
    public List<Mascota> buscarMascotaPorUsuario(@Param("id") Long id);

}
