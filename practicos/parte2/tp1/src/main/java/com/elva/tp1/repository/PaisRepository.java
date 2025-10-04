package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    // findAllBy and findBy are
    List<Pais> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);


}
