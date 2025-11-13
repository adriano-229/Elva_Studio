package com.projects.mycar.mycar_server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO que representa un alquiler con su costo calculado.
 * Se usa para el "pagaré" temporal antes de persistir el pago.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlquilerConCostoDTO {

    private Long alquilerId;
    private String vehiculoPatente;
    private String fechaDesde;
    private String fechaHasta;
    private Integer cantidadDias;
    private Double costoPorDia;
    private Double subtotal;
}
