package com.elva.tp1.repository;

import com.elva.tp1.domain.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalidadRepository extends JpaRepository<Localidad, Long> {
    List<Localidad> findByDepartamentoId(Long departamentoId);
    List<Localidad> findByActivoTrue();
}
