package com.example.mycar.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DetalleFacturaDTO extends BaseDTO {

    private Long alquilerId;
    private Long facturaId;
    private Integer cantidad;
    private Double subtotal;
}

