package com.projects.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Integer cantidadDias;
    private Double costoPorDia;
    private Double subtotal;
    private Long idCliente;
    private Long idVehiculo;
    private DocumentacionDTO documentacion;
    private String codigoDescuento;
    private Double porcentajeDescuento;
    private double descuento;
    private double totalConDescuento;


}
