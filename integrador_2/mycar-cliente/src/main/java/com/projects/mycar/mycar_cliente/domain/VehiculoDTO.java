package com.projects.mycar.mycar_cliente.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoDTO extends BaseDTO {


    private EstadoVehiculo estadoVehiculo;

    private String patente;
    private Long caracteristicaVehiculoId;

    //private CaracteristicaVehiculoDTO caracteristicaVehiculo;

}
