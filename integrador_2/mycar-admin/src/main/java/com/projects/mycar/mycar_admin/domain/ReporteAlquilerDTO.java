package com.projects.mycar.mycar_admin.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteAlquilerDTO extends BaseDTO {

    private String clienteNombreCompleto;
    private String clienteDocumento;
    private String vehiculoPatente;
    private String vehiculoModelo;
    private String vehiculoMarca;
    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private BigDecimal montoTotal;

}
