package com.projects.mycar.mycar_cliente.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

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
