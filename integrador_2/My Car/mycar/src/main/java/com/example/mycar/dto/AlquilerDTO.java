package com.example.mycar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AlquilerDTO extends BaseDTO {
    private Date fechaDesde;
    private Date fechaHasta;
    private Long documentacionId;
    private Long vehiculoId;
    private Double costoCalculado;
    private Integer cantidadDias;
}
