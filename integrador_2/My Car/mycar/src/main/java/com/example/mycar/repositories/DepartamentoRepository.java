package com.example.mycar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import com.example.mycar.entities.Departamento;

@Repository
public interface DepartamentoRepository extends BaseRepository<Departamento, Long> {

    List<Departamento> findAllByActivoTrueOrderByNombreAsc();

    List<Departamento> findAllByActivoTrueAndProvincia_IdOrderByNombre(Long provinciaId);

    List<Departamento> findAllByActivoTrueAndProvincia_NombreOrderByNombre(String provinciaNombre);

    // Para migraciones: buscar por nombre exacto y provincia
    Optional<Departamento> findByActivoTrueAndNombreIgnoreCaseAndProvincia_IdAndProvincia_ActivoTrue(String nombre, Long provinciaId);
}
