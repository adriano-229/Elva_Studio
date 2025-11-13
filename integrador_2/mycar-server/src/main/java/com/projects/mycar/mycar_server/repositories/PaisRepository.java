package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Pais;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    List<Pais> findAllByActivoTrueOrderByNombreAsc();

    List<Pais> findAllByOrderByNombreAsc();

    Optional<Pais> findByActivoTrueAndNombreIgnoreCase(String nombre);
}
