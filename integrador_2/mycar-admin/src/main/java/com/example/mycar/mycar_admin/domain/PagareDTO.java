package com.example.mycar.mycar_admin.domain;

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
    private Double subtotal;
    private Double descuento;
    private Double porcentajeDescuento;
    private String codigoDescuento;
    private Double totalAPagar;
    private LocalDateTime fechaEmision;
    private String clienteNombre;
    private Long clienteId;
}
