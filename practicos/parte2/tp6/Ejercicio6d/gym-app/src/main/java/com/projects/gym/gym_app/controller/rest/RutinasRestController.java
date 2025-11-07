package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.controller.rest.dto.DetalleDiarioCreateRequest;
import com.projects.gym.gym_app.controller.rest.dto.DetalleEjercicioCreateRequest;
import com.projects.gym.gym_app.controller.rest.dto.RutinaCreateRequest;
import com.projects.gym.gym_app.controller.rest.dto.RutinaEstadoRequest;
import com.projects.gym.gym_app.controller.rest.dto.RutinaResponse;
import com.projects.gym.gym_app.controller.rest.mapper.RutinaRestMapper;
import com.projects.gym.gym_app.domain.DetalleEjercicio;
import com.projects.gym.gym_app.domain.Rutina;
import com.projects.gym.gym_app.service.RutinaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/rutinas")
@RequiredArgsConstructor
public class RutinasRestController {

    private final RutinaService rutinaService;

    @GetMapping("/actual")
    public ResponseEntity<RutinaResponse> rutinaActual(@RequestParam Long numeroSocio) {
        Rutina rutina = rutinaService.buscarRutinaActual(numeroSocio);
        if (rutina == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(RutinaRestMapper.toResponse(rutina));
    }

    @GetMapping("/finalizadas")
    public List<RutinaResponse> rutinasFinalizadas(@RequestParam(required = false) Long profesorId,
                                                   @RequestParam(required = false) Long numeroSocio) {
        if (profesorId != null) {
            return rutinaService.listarRutinasFinalizadasPorProfesor(profesorId).stream()
                    .map(RutinaRestMapper::toResponse)
                    .toList();
        }
        if (numeroSocio != null) {
            return rutinaService.listarRutinasFinalizadasPorSocio(numeroSocio).stream()
                    .map(RutinaRestMapper::toResponse)
                    .toList();
        }
        return rutinaService.listarRutinasFinalizadas().stream()
                .map(RutinaRestMapper::toResponse)
                .toList();
    }

    @PostMapping
    public ResponseEntity<RutinaResponse> crear(@Valid @RequestBody RutinaCreateRequest request) {
        Rutina rutina = rutinaService.crearRutina(
                request.numeroSocio(),
                request.profesorId(),
                request.fechaInicia(),
                request.fechaFinaliza(),
                request.objetivo()
        );
        return ResponseEntity.status(201).body(RutinaRestMapper.toResponse(rutina));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RutinaResponse> cambiarEstado(@PathVariable Long id,
                                                        @Valid @RequestBody RutinaEstadoRequest request) {
        rutinaService.modificarEstadoRutina(id, request.estado());
        Rutina actualizada = rutinaService.buscarPorId(id);
        return ResponseEntity.ok(RutinaRestMapper.toResponse(actualizada));
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<RutinaResponse> agregarDetalle(@PathVariable Long id,
                                                         @Valid @RequestBody DetalleDiarioCreateRequest request) {
        rutinaService.crearDetalleDiario(id, request.diaSemana());
        Rutina actualizada = rutinaService.buscarPorId(id);
        return ResponseEntity.ok(RutinaRestMapper.toResponse(actualizada));
    }

    @PostMapping("/{id}/detalles/{detalleId}/ejercicios")
    public ResponseEntity<RutinaResponse> agregarEjercicio(@PathVariable Long id,
                                                           @PathVariable Long detalleId,
                                                           @Valid @RequestBody DetalleEjercicioCreateRequest request) {
        DetalleEjercicio ejercicio = DetalleEjercicio.builder()
                .actividad(request.actividad())
                .series(request.series())
                .repeticiones(request.repeticiones())
                .grupoMuscular(request.grupoMuscular())
                .activo(true)
                .build();
        rutinaService.asignarDetalleEjercicio(detalleId, ejercicio);
        Rutina actualizada = rutinaService.buscarPorId(id);
        return ResponseEntity.ok(RutinaRestMapper.toResponse(actualizada));
    }
}
