package com.projects.mycar.mycar_cliente.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class CostoVehiculoDTO extends BaseDTO{
	
	private Date fechaDesde;
	private Date fechaHasta;
	private BigDecimal costo;

}
