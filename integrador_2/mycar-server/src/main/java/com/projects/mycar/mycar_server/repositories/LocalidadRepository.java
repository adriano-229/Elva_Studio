package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Localidad;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocalidadRepository extends BaseRepository<Localidad, Long> {

    // Todas las localidades activas ordenadas por nombre
    List<Localidad> findAllByActivoTrueOrderByNombreAsc();

    // Localidades activas de un departamento específico
    List<Localidad> findAllByActivoTrueAndDepartamento_IdAndDepartamento_ActivoTrueOrderByNombreAsc(Long departamentoId);

    // Localidades activas filtradas por nombre de departamento
    List<Localidad> findAllByActivoTrueAndDepartamento_NombreAndDepartamento_ActivoTrueOrderByNombreAsc(String departamentoNombre);

    // Buscar una localidad exacta por nombre y departamento (para migraciones)
    Optional<Localidad> findByActivoTrueAndNombreIgnoreCaseAndDepartamento_IdAndDepartamento_ActivoTrue(String nombre, Long departamentoId);
}
