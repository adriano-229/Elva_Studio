package com.example.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ConfiguracionPromocionDTO extends BaseDTO{
    
    private Double porcentajeDescuento;
    private String mensajePromocion;
    private Boolean activa;
}

