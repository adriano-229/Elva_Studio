package com.example.mycar.dto.recordatorios;

import java.util.Date;

/**
 * Proyección utilizada para los recordatorios de devolución.
 */
public record RecordatorioDevolucionDTO(
        Long clienteId,
        String nombreCompleto,
        String email,
        Date fechaDesde,
        Date fechaHasta,
        String marcaVehiculo,
        String modeloVehiculo,
        String patente
) {
}
