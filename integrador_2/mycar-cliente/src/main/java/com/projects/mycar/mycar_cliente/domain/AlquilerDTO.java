package com.projects.mycar.mycar_cliente.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;


@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class AlquilerDTO extends BaseDTO {

    private LocalDate fechaDesde;
    private LocalDate fechaHasta;
    private Long vehiculoId;
    private Long documentacionId;


}
