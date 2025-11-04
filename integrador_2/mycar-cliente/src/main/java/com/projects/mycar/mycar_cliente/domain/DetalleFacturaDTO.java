package com.projects.mycar.mycar_cliente.domain;

import java.math.BigDecimal;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class DetalleFacturaDTO extends BaseDTO{
	
	private int cantidad;
	private Long alquilerId;
	private BigDecimal subtotal;

}
