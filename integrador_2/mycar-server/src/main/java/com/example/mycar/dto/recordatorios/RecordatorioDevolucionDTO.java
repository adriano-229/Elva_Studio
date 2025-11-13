package com.example.mycar.dto.recordatorios;

import java.time.LocalDate;

/**
 * Proyección utilizada para los recordatorios de devolución.
 */
public record RecordatorioDevolucionDTO(
        Long clienteId,
        String nombreCompleto,
        String email,
        LocalDate fechaDesde,
        LocalDate fechaHasta,
        String marcaVehiculo,
        String modeloVehiculo,
        String patente
) {
}
