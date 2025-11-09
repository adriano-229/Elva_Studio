package com.example.mycar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.mycar.entities.Pais;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    List<Pais> findAllByActivoTrueOrderByNombreAsc();
    
    List<Pais> findAllByOrderByNombreAsc();

    Optional<Pais> findByActivoTrueAndNombreIgnoreCase(String nombre);
}
