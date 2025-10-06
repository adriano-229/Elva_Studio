package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends BaseRepository<Departamento, Long> {

    List<Departamento> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);

    List<Departamento> findAllByProvincia_NombreOrderByNombre(String provinciaNombre);

    List<Departamento> findAllByProvincia_IdOrderByNombre(Long provinciaId);

    List<Departamento> findAllByOrderByNombreAsc();

    // Nuevos métodos excluyendo eliminados
    List<Departamento> findAllByEliminadoFalseOrderByNombreAsc();
    List<Departamento> findAllByEliminadoFalseAndProvincia_IdOrderByNombre(Long provinciaId);
    List<Departamento> findAllByEliminadoFalseAndProvincia_NombreOrderByNombre(String provinciaNombre);
}
