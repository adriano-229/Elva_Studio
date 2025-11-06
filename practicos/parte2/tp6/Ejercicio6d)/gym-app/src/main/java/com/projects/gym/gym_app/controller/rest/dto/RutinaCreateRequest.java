package com.projects.gym.gym_app.controller.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RutinaCreateRequest(
        @NotNull Long numeroSocio,
        @NotNull Long profesorId,
        @NotNull LocalDate fechaInicia,
        @NotNull LocalDate fechaFinaliza,
        @NotBlank String objetivo
) {}
