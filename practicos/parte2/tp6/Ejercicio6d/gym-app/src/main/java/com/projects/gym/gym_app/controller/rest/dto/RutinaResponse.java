package com.projects.gym.gym_app.controller.rest.dto;

import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import java.time.LocalDate;
import java.util.List;

public record RutinaResponse(
        Long id,
        Long numeroSocio,
        String socioNombre,
        Long profesorId,
        String profesorNombre,
        LocalDate fechaInicia,
        LocalDate fechaFinaliza,
        String objetivo,
        EstadoRutina estado,
        List<RutinaDetalleResponse> detalles
) {}
