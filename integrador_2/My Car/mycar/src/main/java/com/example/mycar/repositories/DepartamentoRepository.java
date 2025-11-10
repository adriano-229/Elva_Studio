package com.example.mycar.repositories;

import com.example.mycar.entities.Departamento;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends BaseRepository<Departamento, Long> {

    List<Departamento> findAllByActivoTrueOrderByNombreAsc();

    List<Departamento> findAllByActivoTrueAndProvincia_IdOrderByNombre(Long provinciaId);

    List<Departamento> findAllByActivoTrueAndProvincia_NombreOrderByNombre(String provinciaNombre);

    // Para migraciones: buscar por nombre exacto y provincia
    Optional<Departamento> findByActivoTrueAndNombreIgnoreCaseAndProvincia_IdAndProvincia_ActivoTrue(String nombre, Long provinciaId);
}
