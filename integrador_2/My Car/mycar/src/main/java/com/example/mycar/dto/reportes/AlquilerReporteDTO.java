package com.example.mycar.dto.reportes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Proyección inmutable para el reporte de alquileres por período.
 */
public record AlquilerReporteDTO(
        String clienteNombreCompleto,
        String clienteDocumento,
        String vehiculoPatente,
        String vehiculoModelo,
        String vehiculoMarca,
        Date fechaDesde,
        Date fechaHasta,
        Double montoTotal
) {
}
