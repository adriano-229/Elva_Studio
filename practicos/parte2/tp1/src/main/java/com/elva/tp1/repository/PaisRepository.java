package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaisRepository extends BaseRepository<Pais, Long> {

    // findAllBy and findBy generate the same query: but they differ in their explicitness
    List<Pais> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);

    List<Pais> findAllByOrderByNombreAsc();

}
