package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Direccion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DireccionRepository extends BaseRepository<Direccion, Long> {

    List<Direccion> findAllByDepartamento_NombreOrderByCalleAsc(String departamentoNombre);

    List<Direccion> findAllByOrderByCalleAsc();

    // Para migraciones: buscar por calle exacta, altura y departamento
    Optional<Direccion> findByCalleIgnoreCaseAndAlturaAndDepartamento(String calle, Integer altura, Departamento departamento);
}
