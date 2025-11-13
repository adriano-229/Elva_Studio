package com.example.mycar.repositories;

import com.example.mycar.entities.FormaDePago;
import com.example.mycar.enums.TipoPago;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FormaDePagoRepository extends BaseRepository<FormaDePago, Long> {

    Optional<FormaDePago> findByTipoPagoAndActivoTrue(TipoPago tipoPago);
}

