package com.projects.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
