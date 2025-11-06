package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    List<Pais> findAllByOrderByNombreAsc();

    List<Pais> findAllByEliminadoFalseOrderByNombreAsc();

    Optional<Pais> findByNombreIgnoreCase(String nombre);
}
