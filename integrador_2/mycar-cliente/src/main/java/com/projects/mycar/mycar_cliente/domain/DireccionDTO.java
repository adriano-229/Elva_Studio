package com.projects.mycar.mycar_cliente.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class DireccionDTO extends BaseDTO{
    
    @NotBlank @Size(max = 120)
    private String calle;

    @NotBlank @Size(max = 20)
    private String numeracion;

    @Size(max = 120)
    private String barrio;

    @Size(max = 30)
    private String manzanaPiso;

    @Size(max = 30)
    private String casaDepartamento;

    @Size(max = 255)
    private String referencia;

    @NotBlank
    private String localidadId; // relación
    
    @NotBlank
    private String departamentoId;
    
    @NotBlank
    private String provinciaId;
    
    @NotBlank
    private String paisId;
}
