package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends BaseRepository<Departamento, Long> {

    List<Departamento> findAllByNombreIsContainingIgnoreCaseOrderByNombre(String nombre);

    List<Departamento> findAllByProvincia_NombreOrderByNombre(String provinciaNombre);

    List<Departamento> findAllByOrderByNombreAsc();

}
