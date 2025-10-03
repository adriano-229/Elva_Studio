package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.domain.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    Optional<Pais> findByNombreOrderByAsc(String nombreDepartamento);

    Optional<Pais> findByNombreProvinciaOrderByAsc(String nombreProvincia);

    ;


}
