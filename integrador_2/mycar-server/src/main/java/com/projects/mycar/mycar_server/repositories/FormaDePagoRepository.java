package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.FormaDePago;
import com.projects.mycar.mycar_server.enums.TipoPago;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaDePagoRepository extends BaseRepository<FormaDePago, Long> {

    Optional<FormaDePago> findByTipoPagoAndActivoTrue(TipoPago tipoPago);
}

