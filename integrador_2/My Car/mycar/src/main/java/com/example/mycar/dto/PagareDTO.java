package com.example.mycar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO que representa el "pagaré" temporal.
 * No se persiste, solo se muestra al cliente antes del pago.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PagareDTO {

    private List<AlquilerConCostoDTO> alquileres;
    private Double totalAPagar;
    private LocalDateTime fechaEmision;
    private String clienteNombre;
    private Long clienteId;
}
