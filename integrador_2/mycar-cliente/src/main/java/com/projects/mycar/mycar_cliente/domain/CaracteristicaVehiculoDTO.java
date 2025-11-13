package com.projects.mycar.mycar_cliente.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaracteristicaVehiculoDTO extends BaseDTO {

    private String marca;

    private String modelo;

    private int cantidadPuerta;

    private int cantidadAsiento;

    private long anio;

    private int cantidadTotalVehiculo;

    private int cantidadVehiculoAlquilado;

    private ImagenDTO imagen;

    //private CostoVehiculoDTO costoVehiculo;
}
