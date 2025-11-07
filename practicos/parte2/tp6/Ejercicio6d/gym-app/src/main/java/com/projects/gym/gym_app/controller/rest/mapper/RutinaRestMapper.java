package com.projects.gym.gym_app.controller.rest.mapper;

import java.util.Collections;
import java.util.List;

import com.projects.gym.gym_app.controller.rest.dto.RutinaDetalleResponse;
import com.projects.gym.gym_app.controller.rest.dto.RutinaEjercicioResponse;
import com.projects.gym.gym_app.controller.rest.dto.RutinaResponse;
import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.DetalleEjercicio;
import com.projects.gym.gym_app.domain.Rutina;

public final class RutinaRestMapper {

    private RutinaRestMapper() {}

    public static RutinaResponse toResponse(Rutina rutina) {
        if (rutina == null) {
            return null;
        }

        Long numeroSocio = rutina.getSocio() != null ? rutina.getSocio().getNumeroSocio() : null;
        String socioNombre = rutina.getSocio() != null
                ? rutina.getSocio().getNombre() + " " + rutina.getSocio().getApellido()
                : null;
        Long profesorId = rutina.getProfesor() != null ? rutina.getProfesor().getId() : null;
        String profesorNombre = rutina.getProfesor() != null
                ? rutina.getProfesor().getNombre() + " " + rutina.getProfesor().getApellido()
                : null;

        List<RutinaDetalleResponse> detalles = rutina.getDetallesDiarios() == null
                ? Collections.emptyList()
                : rutina.getDetallesDiarios().stream()
                .map(RutinaRestMapper::toDetalleResponse)
                .toList();

        return new RutinaResponse(
                rutina.getId(),
                numeroSocio,
                socioNombre,
                profesorId,
                profesorNombre,
                rutina.getFechaInicia(),
                rutina.getFechaFinaliza(),
                rutina.getObjetivo(),
                rutina.getEstadoRutina(),
                detalles
        );
    }

    private static RutinaDetalleResponse toDetalleResponse(DetalleDiario detalle) {
        List<RutinaEjercicioResponse> ejercicios = detalle.getDetalleEjercicios() == null
                ? Collections.emptyList()
                : detalle.getDetalleEjercicios().stream()
                .map(RutinaRestMapper::toEjercicioResponse)
                .toList();

        return new RutinaDetalleResponse(
                detalle.getId(),
                detalle.getDiaSemana(),
                ejercicios
        );
    }

    private static RutinaEjercicioResponse toEjercicioResponse(DetalleEjercicio ejercicio) {
        return new RutinaEjercicioResponse(
                ejercicio.getId(),
                ejercicio.getActividad(),
                ejercicio.getSeries(),
                ejercicio.getRepeticiones(),
                ejercicio.getGrupoMuscular(),
                ejercicio.isActivo()
        );
    }
}
