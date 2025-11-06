package com.example.mycar.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class DireccionDTO extends BaseDTO {

    @NotBlank
    @Size(max = 120)
    private String calle;

    @NotBlank
    @Size(max = 20)
    private String numeracion;

    @Size(max = 120)
    private String barrio;

    @Size(max = 30)
    private String manzana_piso;

    @Size(max = 30)
    private String casa_departamento;

    @Size(max = 255)
    private String referencia;

    @NotBlank
    private String localidadId; // relación

}
