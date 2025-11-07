package com.projects.gym.gym_app.controller.rest.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeEstadoRequest(@NotNull Boolean activo) {}
