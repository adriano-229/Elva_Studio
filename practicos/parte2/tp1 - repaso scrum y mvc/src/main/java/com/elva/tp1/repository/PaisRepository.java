package com.elva.tp1.repository;

import com.elva.tp1.entity.Pais;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaisRepository extends JpaRepository<Pais, Integer> {
    List<Pais> findByActivoTrueOrderByNombreAsc();
}
