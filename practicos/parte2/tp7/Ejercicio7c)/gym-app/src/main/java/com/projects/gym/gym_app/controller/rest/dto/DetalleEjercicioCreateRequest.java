package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.GrupoMuscular;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DetalleEjercicioCreateRequest(
        @NotBlank String actividad,
        @NotNull @Min(1) Integer series,
        @NotNull @Min(1) Integer repeticiones,
        @NotNull GrupoMuscular grupoMuscular
) {}
