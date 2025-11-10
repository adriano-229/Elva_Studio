package com.example.mycar.repositories;

import com.example.mycar.entities.CodigoDescuento;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoDescuentoRepository extends BaseRepository<CodigoDescuento, Long> {
    Optional<CodigoDescuento> findByCodigoAndActivoTrue(String codigo);

    Optional<CodigoDescuento> findByClienteIdAndUtilizadoFalseAndActivoTrue(Long clienteId);
}

