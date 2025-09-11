package edu.egg.tinder.repository;

import edu.egg.tinder.entities.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositorioVoto extends JpaRepository<Voto, Long> {

    @Query("SELECT v FROM Voto v WHERE v.mascota1.id = :id ORDER BY v.fecha DESC")
    List<Voto> buscarVotosPropios(@Param("id") Long id);

    @Query("SELECT v FROM Voto v WHERE v.mascota2.id = :id ORDER BY v.fecha DESC")
    List<Voto> buscarVotosRecibidos(@Param("id") Long id);
}
