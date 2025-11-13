package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Direccion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionRepository extends BaseRepository<Direccion, Long> {

    List<Direccion> findAllByActivoTrueAndLocalidad_NombreAndLocalidad_ActivoTrueOrderByCalleAsc(String departamentoNombre);

    List<Direccion> findAllByActivoTrueOrderByCalleAsc();

    // Para migraciones: buscar por calle exacta, altura y departamento
    Optional<Direccion> findByActivoTrueAndCalleIgnoreCaseAndNumeracionAndLocalidad_IdAndLocalidad_ActivoTrue(String calle, Integer numeracion, Long localidadId);

}
