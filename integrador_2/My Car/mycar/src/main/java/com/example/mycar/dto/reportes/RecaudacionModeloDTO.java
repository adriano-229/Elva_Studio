package com.example.mycar.dto.reportes;

/**
 * Resumen de recaudación agrupada por modelo de vehículo.
 */
public record RecaudacionModeloDTO(
        String modelo,
        String marca,
        long vehiculosTotales,
        long cantidadAlquileres,
        Double montoTotal
) {
}
