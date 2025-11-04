package com.projects.mycar.mycar_cliente.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class NacionalidadDTO extends BaseDTO{
	private String nombre;
	private Long clienteId;
	

}
