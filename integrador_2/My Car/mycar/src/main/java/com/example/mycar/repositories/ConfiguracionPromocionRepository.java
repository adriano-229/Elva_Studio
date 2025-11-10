package com.example.mycar.repositories;

import com.example.mycar.entities.ConfiguracionPromocion;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionPromocionRepository extends BaseRepository<ConfiguracionPromocion, Long> {
    Optional<ConfiguracionPromocion> findFirstByActivaTrueAndActivoTrueOrderByIdDesc();
}
