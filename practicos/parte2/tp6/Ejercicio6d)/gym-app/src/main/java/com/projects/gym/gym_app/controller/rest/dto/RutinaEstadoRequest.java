package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import jakarta.validation.constraints.NotNull;

public record RutinaEstadoRequest(@NotNull EstadoRutina estado) {}
