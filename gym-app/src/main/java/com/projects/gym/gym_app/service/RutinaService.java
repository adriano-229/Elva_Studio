package com.projects.gym.gym_app.service;

import com.projects.gym.gym_app.domain.DetalleDiario;
import com.projects.gym.gym_app.domain.DetalleEjercicio;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;

import java.time.LocalDate;

public interface RutinaService {

    Rutina buscarRutinaActual(Long socioId);

    Rutina buscarPorId(Long rutinaId);

    Rutina crearRutina(Long numeroSocio, Long profesorId, LocalDate fechaInicia, LocalDate fechaFinaliza, String objetivo);

    void modificarEstadoRutina(Long rutinaId, EstadoRutina nuevoEstado);

    DetalleDiario crearDetalleDiario(Long rutinaId, int numeroDia);

    DetalleEjercicio asignarDetalleEjercicio(Long detalleDiarioId, DetalleEjercicio ejercicio);
}
