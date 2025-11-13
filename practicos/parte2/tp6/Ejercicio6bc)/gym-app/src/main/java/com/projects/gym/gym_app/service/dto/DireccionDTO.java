package com.projects.gym.gym_app.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DireccionDTO {
    private String id;

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

    private boolean eliminado;
}
