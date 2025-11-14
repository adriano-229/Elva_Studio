package com.projects.mycar.mycar_admin.domain;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteRecaudacionModeloDTO extends BaseDTO {

    private String modelo;
    private String marca;
    private Integer vehiculosTotales;
    private Integer cantidadAlquileres;
    private BigDecimal montoTotal;

}
