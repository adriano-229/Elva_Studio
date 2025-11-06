package com.elva.tp1.repository;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.domain.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {


    List<Provincia> findAllByOrderByNombreAsc();

    // Excluyendo eliminados
    List<Provincia> findAllByEliminadoFalseOrderByNombreAsc();

    List<Provincia> findAllByEliminadoFalseAndPais_IdOrderByNombreAsc(Long paisId);

    List<Provincia> findAllByEliminadoFalseAndPais_NombreOrderByNombreAsc(String paisNombre);

    // Para migraciones: buscar por nombre exacto y país
    Optional<Provincia> findByNombreIgnoreCaseAndPais(String nombre, Pais pais);
}
