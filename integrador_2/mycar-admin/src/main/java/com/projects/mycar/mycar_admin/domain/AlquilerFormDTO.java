package com.projects.mycar.mycar_admin.domain;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlquilerFormDTO {

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
