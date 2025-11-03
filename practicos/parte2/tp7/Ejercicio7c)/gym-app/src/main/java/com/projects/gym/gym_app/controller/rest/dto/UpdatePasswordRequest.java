package com.projects.gym.gym_app.controller.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordRequest(
        @NotBlank String currentPassword,
        @NotBlank @Size(min = 6, message = "La nueva clave debe tener al menos 6 caracteres") String newPassword
) {}
