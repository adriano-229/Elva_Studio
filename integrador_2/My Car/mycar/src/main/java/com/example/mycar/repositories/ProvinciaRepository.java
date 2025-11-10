package com.example.mycar.repositories;

import com.example.mycar.entities.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProvinciaRepository extends BaseRepository<Provincia, Long> {


    List<Provincia> findAllByActivoTrueOrderByNombreAsc();

    List<Provincia> findAllByActivoTrueAndPais_IdAndPais_ActivoTrueOrderByNombreAsc(Long paisId);

    List<Provincia> findAllByActivoTrueAndPais_NombreAndPais_ActivoTrueOrderByNombreAsc(String paisNombre);

    // Para migraciones: buscar por nombre exacto y país
    Optional<Provincia> findByActivoTrueAndNombreIgnoreCaseAndPais_IdAndPais_ActivoTrue(String nombre, Long paisId);
}