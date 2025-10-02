package com.elva.tp1.repository;

import com.elva.tp1.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByLocalidadId(Long localidadId);
    List<Direccion> findByActivoTrue();
}
