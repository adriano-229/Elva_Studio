package com.projects.gym.gym_app.service.impl;

import com.projects.gym.gym_app.domain.*;
import com.projects.gym.gym_app.domain.enums.DiaSemana;
import com.projects.gym.gym_app.domain.enums.EstadoRutina;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.repository.DetalleDiarioRepository;
import com.projects.gym.gym_app.repository.DetalleEjercicioRepository;
import com.projects.gym.gym_app.repository.EmpleadoRepository;
import com.projects.gym.gym_app.repository.RutinaRepository;
import com.projects.gym.gym_app.repository.SocioRepository;
import com.projects.gym.gym_app.service.RutinaService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RutinaServiceImpl implements RutinaService {

    private final RutinaRepository rutinaRepo;
    private final SocioRepository socioRepo;
    private final EmpleadoRepository empleadoRepo;
    private final DetalleDiarioRepository detalleDiarioRepo;
    private final DetalleEjercicioRepository detalleEjercicioRepo;

    @Override
    public Rutina buscarRutinaActual(Long socioId) {
        Rutina rutina = rutinaRepo
                .findBySocio_NumeroSocioAndEstadoRutina(socioId, EstadoRutina.EN_PROCESO)
                .orElse(null);

        if (rutina == null) {
            return null;
        }

        actualizarEstadoSegunFechas(rutina);
        return rutina;
    }

    @Override
    @Transactional(readOnly = true)
    public Rutina buscarPorId(Long rutinaId) {
        Rutina rutina = rutinaRepo.findById(rutinaId)
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada: " + rutinaId));
        actualizarEstadoSegunFechas(rutina);
        return rutina;
    }

    @Override
    public Rutina crearRutina(Long numeroSocio, Long profesorId, LocalDate fechaInicia, LocalDate fechaFinaliza, String objetivo) {
        if (numeroSocio == null || numeroSocio <= 0) {
            throw new IllegalArgumentException("Número de socio inválido");
        }
        if (profesorId == null || profesorId <= 0) {
            throw new IllegalArgumentException("Profesor inválido");
        }
        if (fechaInicia == null || fechaFinaliza == null) {
            throw new IllegalArgumentException("Las fechas de inicio y fin son obligatorias");
        }
        if (fechaFinaliza.isBefore(fechaInicia)) {
            throw new IllegalArgumentException("La fecha de finalización no puede ser anterior a la de inicio");
        }

        Socio socio = socioRepo.findByNumeroSocio(numeroSocio)
                .orElseThrow(() -> new EntityNotFoundException("Socio no encontrado: " + numeroSocio));

        Empleado profesor = empleadoRepo.findById(profesorId)
                .orElseThrow(() -> new EntityNotFoundException("Profesor no encontrado: " + profesorId));

        if (!profesor.isActivo() || profesor.getTipo() != TipoEmpleado.PROFESOR) {
            throw new IllegalArgumentException("El empleado seleccionado no es un profesor activo");
        }

        rutinaRepo.findBySocio_NumeroSocioAndEstadoRutina(numeroSocio, EstadoRutina.EN_PROCESO)
                .ifPresent(actual -> {
                    actual.setEstadoRutina(EstadoRutina.ANULADA);
                    actual.setActivo(false);
                });

        Rutina rutina = new Rutina();
        rutina.setFechaInicia(fechaInicia);
        rutina.setFechaFinaliza(fechaFinaliza);
        rutina.setObjetivo(objetivo);
        rutina.setActivo(true);
        rutina.setEstadoRutina(EstadoRutina.EN_PROCESO);
        rutina.setSocio(socio);
        rutina.setProfesor(profesor);
        rutina.setDetallesDiarios(new ArrayList<>());

        return rutinaRepo.save(rutina);
    }

    @Override
    public void modificarEstadoRutina(Long rutinaId, EstadoRutina nuevoEstado) {
        Rutina r = rutinaRepo.findById(rutinaId)
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada: " + rutinaId));
        r.setEstadoRutina(nuevoEstado);
        r.setActivo(nuevoEstado == EstadoRutina.EN_PROCESO);
    }

    @Override
    public DetalleDiario crearDetalleDiario(Long rutinaId, DiaSemana diaSemana) {
        Rutina r = rutinaRepo.findById(rutinaId)
                .orElseThrow(() -> new EntityNotFoundException("Rutina no encontrada: " + rutinaId));

        if (r.getEstadoRutina() != EstadoRutina.EN_PROCESO) {
            throw new IllegalStateException("Solo se pueden agregar días a rutinas en proceso");
        }

        if (r.getDetallesDiarios() != null && r.getDetallesDiarios().stream()
                .anyMatch(d -> d.getDiaSemana() == diaSemana)) {
            throw new IllegalArgumentException("Ya existe un día " + diaSemana.getDisplayName() + " para esta rutina");
        }

        DetalleDiario d = DetalleDiario.builder()
                .diaSemana(diaSemana)
                .activo(true)
                .rutina(r)
                .build();

        DetalleDiario guardado = detalleDiarioRepo.save(d);
        if (r.getDetallesDiarios() == null) {
            r.setDetallesDiarios(new ArrayList<>());
        }
        r.getDetallesDiarios().add(guardado);
        return guardado;
    }

    @Override
    public DetalleEjercicio asignarDetalleEjercicio(Long detalleDiarioId, DetalleEjercicio ejercicio) {
        DetalleDiario d = detalleDiarioRepo.findById(detalleDiarioId)
                .orElseThrow(() -> new EntityNotFoundException("Detalle diario no encontrado: " + detalleDiarioId));

        Rutina rutina = d.getRutina();
        if (rutina == null || rutina.getEstadoRutina() != EstadoRutina.EN_PROCESO) {
            throw new IllegalStateException("La rutina no se encuentra en proceso");
        }

        ejercicio.setDetalleDiario(d);
        ejercicio.setActivo(true);

        DetalleEjercicio guardado = detalleEjercicioRepo.save(ejercicio);
        if (d.getDetalleEjercicios() == null) {
            d.setDetalleEjercicios(new ArrayList<>());
        }
        d.getDetalleEjercicios().add(guardado);
        return guardado;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rutina> listarRutinasFinalizadas() {
        return rutinaRepo.findByEstadoRutinaOrderByFechaFinalizaDesc(EstadoRutina.FINALIZADA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rutina> listarRutinasFinalizadasPorProfesor(Long profesorId) {
        if (profesorId == null) {
            return List.of();
        }
        return rutinaRepo.findByProfesor_IdAndEstadoRutinaOrderByFechaFinalizaDesc(profesorId, EstadoRutina.FINALIZADA);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rutina> listarRutinasFinalizadasPorSocio(Long numeroSocio) {
        if (numeroSocio == null) {
            return List.of();
        }
        return rutinaRepo.findBySocio_NumeroSocioAndEstadoRutinaOrderByFechaFinalizaDesc(numeroSocio, EstadoRutina.FINALIZADA);
    }

    private void actualizarEstadoSegunFechas(Rutina rutina) {
        if (rutina.getEstadoRutina() == EstadoRutina.EN_PROCESO
                && rutina.getFechaFinaliza() != null
                && LocalDate.now().isAfter(rutina.getFechaFinaliza())) {
            rutina.setEstadoRutina(EstadoRutina.FINALIZADA);
            rutina.setActivo(false);
        }
    }
}
