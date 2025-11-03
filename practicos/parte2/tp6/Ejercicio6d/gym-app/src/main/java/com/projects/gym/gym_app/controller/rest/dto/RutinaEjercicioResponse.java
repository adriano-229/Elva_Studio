package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.GrupoMuscular;

public record RutinaEjercicioResponse(
        Long id,
        String actividad,
        Integer series,
        Integer repeticiones,
        GrupoMuscular grupoMuscular,
        boolean activo
) {}
