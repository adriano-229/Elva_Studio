package com.projects.mycar.mycar_cliente.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;
import lombok.*;


@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoDTO extends BaseDTO {


    private EstadoVehiculo estadoVehiculo;

    private String patente;

    //private Long caracteristicaVehiculoId;

    private CaracteristicaVehiculoDTO caracteristicaVehiculo;

}
