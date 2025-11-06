package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Provincia;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartamentoRepository extends BaseRepository<Departamento, Long> {

    List<Departamento> findAllByOrderByNombreAsc();

    // Nuevos métodos excluyendo eliminados
    List<Departamento> findAllByEliminadoFalseAndProvincia_IdOrderByNombre(Long provinciaId);

    List<Departamento> findAllByEliminadoFalseAndProvincia_NombreOrderByNombre(String provinciaNombre);

    // Para migraciones: buscar por nombre exacto y provincia
    Optional<Departamento> findByNombreIgnoreCaseAndProvincia(String nombre, Provincia provincia);
}
