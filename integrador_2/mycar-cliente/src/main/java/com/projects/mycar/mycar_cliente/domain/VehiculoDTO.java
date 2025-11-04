package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class VehiculoDTO extends BaseDTO{
	
	private EstadoVehiculo estadoVehiculo;
	private String patente;
	private Long caracteristicaVehiculoId;
	

}
