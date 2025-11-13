package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.ConfiguracionPromocion;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracionPromocionRepository extends BaseRepository<ConfiguracionPromocion, Long> {
    Optional<ConfiguracionPromocion> findFirstByActivaTrueAndActivoTrueOrderByIdDesc();
}
