package com.elva.tp1.repository;

import com.elva.tp1.domain.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {

    List<Provincia> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);

    List<Provincia> findAllByPais_NombreOrderByNombre(String paisNombre);


}
