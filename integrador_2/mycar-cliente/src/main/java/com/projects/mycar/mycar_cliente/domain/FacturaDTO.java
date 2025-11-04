package com.projects.mycar.mycar_cliente.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.projects.mycar.mycar_cliente.domain.enums.EstadoFactura;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class FacturaDTO extends BaseDTO{
	private Long numeroFactura;
	private LocalDate fechaFactura;
	private BigDecimal totalPagado;
	private EstadoFactura estadoFactura;
	private List<DetalleFacturaDTO> detalles;
	private Long socioId;
	private String nombreCliente;
	private String formaDePagoId;
	private String formaDePagoTexto;
	
}
