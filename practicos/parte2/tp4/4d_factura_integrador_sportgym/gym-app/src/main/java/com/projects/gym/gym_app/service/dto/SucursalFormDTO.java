package com.projects.gym.gym_app.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SucursalFormDTO {

    private String id;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    @NotBlank
    private String empresaId;
}
