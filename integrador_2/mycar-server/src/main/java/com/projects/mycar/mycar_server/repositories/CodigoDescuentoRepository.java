package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.CodigoDescuento;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CodigoDescuentoRepository extends BaseRepository<CodigoDescuento, Long> {
    Optional<CodigoDescuento> findByCodigoIgnoreCaseAndActivoTrue(String codigo);

    Optional<CodigoDescuento> findByClienteIdAndUtilizadoFalseAndActivoTrue(Long clienteId);
}

