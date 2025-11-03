package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.DiaSemana;
import java.util.List;

public record RutinaDetalleResponse(
        Long id,
        DiaSemana diaSemana,
        List<RutinaEjercicioResponse> ejercicios
) {}
