package com.example.mycar.mycar_admin.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
