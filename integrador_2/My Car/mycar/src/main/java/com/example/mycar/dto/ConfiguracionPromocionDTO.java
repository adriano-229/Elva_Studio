package com.example.mycar.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConfiguracionPromocionDTO {
    private Long id;
    private Double porcentajeDescuento;
    private String mensajePromocion;
    private Boolean activa;
}

