package com.example.mycar.dto;

import com.example.mycar.enums.EstadoFactura;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FacturaDTO extends BaseDTO {

    private Long numeroFactura;
    private LocalDate fechaFactura;
    private Double totalPagado;
    private EstadoFactura estado;
    private Long formaDePagoId;
    private String formaDePagoTexto;
    private String observacionPago;
    private String observacionAnulacion;
    private List<DetalleFacturaDTO> detalles;
}
