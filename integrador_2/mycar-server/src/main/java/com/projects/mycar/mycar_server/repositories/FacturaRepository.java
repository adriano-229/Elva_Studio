package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Factura;
import com.projects.mycar.mycar_server.enums.EstadoFactura;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FacturaRepository extends BaseRepository<Factura, Long> {

    // Obtener facturas pendientes de aprobación
    List<Factura> findByEstadoAndActivoTrue(EstadoFactura estado);

    // Obtener el último número de factura para generar el siguiente
    Optional<Factura> findTopByOrderByNumeroFacturaDesc();

    // Buscar factura por número
    Optional<Factura> findByNumeroFacturaAndActivoTrue(Long numeroFactura);
}

