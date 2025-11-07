package com.projects.mycar.mycar_cliente.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class CaracteristicaVehiculo extends BaseDTO {

    private String marca;
    private String modelo;
    private int cantidadPuerta;
    private int cantidadAsiento;
    private Long anio;
    private int cantidadTotalVehiculo;
    private int cantidadVehiculoAlquilado;
    private Long imagenId;
    private Long costoVehiculoId;
}
