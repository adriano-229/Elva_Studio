package com.projects.mycar.mycar_cliente.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class AlquilerDTO extends BaseDTO {

    private Date fechaDesde;
    private Date fechaHasta;
    private Long vehiculoId;
    private Long documentacionId;


}
