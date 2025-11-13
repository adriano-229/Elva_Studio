package com.example.mycar.mycar_admin.domain;

import com.example.mycar.mycar_admin.domain.enums.EstadoFactura;
import com.example.mycar.mycar_admin.domain.enums.TipoPago;
import lombok.*;

@Getter
@Setter
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
