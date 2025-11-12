package com.example.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

import com.example.mycar.mycar_admin.domain.enums.EstadoFactura;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FacturaDTO extends BaseDTO {

    private Long numeroFactura;
    private LocalDate fechaFactura;
    private Double subtotal;
    private Double descuento;
    private Double porcentajeDescuento;
    private String codigoDescuentoCodigo;
    private Double totalPagado;
    private EstadoFactura estado;
    private Long formaDePagoId;
    private String formaDePagoTexto;
    private String observacionPago;
    private String observacionAnulacion;
    private List<DetalleFacturaDTO> detalles;
}
