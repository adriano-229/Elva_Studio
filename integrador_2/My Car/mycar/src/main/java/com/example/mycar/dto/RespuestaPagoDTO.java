package com.example.mycar.dto;

import com.example.mycar.enums.EstadoFactura;
import com.example.mycar.enums.TipoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaPagoDTO {
    private Long facturaId;
    private String numeroFactura;
    private BigDecimal totalPagado;
    private EstadoFactura estado;
    private TipoPago tipoPago;
    private String mensaje;
    private String urlPagoMercadoPago;
}
