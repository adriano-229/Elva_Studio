package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.DiaSemana;
import jakarta.validation.constraints.NotNull;

public record DetalleDiarioCreateRequest(@NotNull DiaSemana diaSemana) {}
