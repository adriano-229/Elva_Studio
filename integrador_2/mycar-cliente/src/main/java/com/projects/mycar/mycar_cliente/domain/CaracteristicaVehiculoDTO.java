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
    
    // pruebo este get para poder mostrar imagen en el front
    // nose que tan buena practica es poner el localost aca pero funciona 

    public String getUrlImagen() {
        if (imagen != null && imagen.getId() != null) {
            return "http://localhost:9000/api/v1/imagen/binario/" + imagen.getId();
        } else {
            return "/assets/img/auto/default-car.jpg";
        }
    }

    private CostoVehiculoDTO costoVehiculo;
}
