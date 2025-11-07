package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.service.SocioService;
import com.projects.gym.gym_app.service.dto.SocioFormDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/socios")
@RequiredArgsConstructor
public class SociosRestController {

    private final SocioService socioService;

    @GetMapping
    public Page<SocioFormDTO> listar(@RequestParam(required = false) String q,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("apellido", "nombre"));
        return socioService.listar(q, pageable);
    }

    @GetMapping("/{id}")
    public SocioFormDTO buscar(@PathVariable String id) {
        return socioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<SocioFormDTO> crear(@Valid @RequestBody SocioFormDTO dto) {
        SocioFormDTO creado = socioService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public SocioFormDTO actualizar(@PathVariable String id, @Valid @RequestBody SocioFormDTO dto) {
        return socioService.modificar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        socioService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
