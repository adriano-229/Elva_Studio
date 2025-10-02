package com.elva.tp1.repository;

import com.elva.tp1.domain.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    List<Departamento> findByProvinciaId(Long provinciaId);
    List<Departamento> findByActivoTrue();
}
