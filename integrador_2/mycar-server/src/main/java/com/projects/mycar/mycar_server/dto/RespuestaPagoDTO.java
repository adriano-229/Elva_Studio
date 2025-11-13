package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.EstadoFactura;
import com.projects.mycar.mycar_server.enums.TipoPago;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RespuestaPagoDTO {
    private Long facturaId;
    private String numeroFactura;
    private Double totalPagado;
    private EstadoFactura estado;
    private TipoPago tipoPago;
    private String mensaje;
    private String urlPagoMercadoPago;
}
