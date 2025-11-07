package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.controller.rest.dto.ChangeEstadoRequest;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.service.EmpleadoService;
import com.projects.gym.gym_app.service.dto.EmpleadoFormDTO;
import com.projects.gym.gym_app.service.dto.EmpleadoListadoDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/empleados")
@RequiredArgsConstructor
public class EmpleadosRestController {

    private final EmpleadoService empleadoService;

    @GetMapping
    public List<EmpleadoListadoDTO> listar(@RequestParam(required = false) String q,
                                           @RequestParam(required = false) TipoEmpleado tipo) {
        return empleadoService.listar(q, tipo);
    }

    @GetMapping("/{id}")
    public EmpleadoFormDTO buscar(@PathVariable Long id) {
        return empleadoService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<EmpleadoFormDTO> crear(@Valid @RequestBody EmpleadoFormDTO dto) {
        EmpleadoFormDTO creado = empleadoService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public EmpleadoFormDTO actualizar(@PathVariable Long id, @Valid @RequestBody EmpleadoFormDTO dto) {
        return empleadoService.actualizar(id, dto);
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id,
                                              @Valid @RequestBody ChangeEstadoRequest request) {
        empleadoService.cambiarEstado(id, request.activo());
        return ResponseEntity.noContent().build();
    }
}
