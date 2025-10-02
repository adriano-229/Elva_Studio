package com.elva.tp1.repository;

import com.elva.tp1.domain.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {
    List<Provincia> findByPaisId(Long paisId);
    List<Provincia> findByActivoTrue();
}
