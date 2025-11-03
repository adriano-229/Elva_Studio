package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.service.SucursalService;
import com.projects.gym.gym_app.service.dto.SucursalDTO;
import com.projects.gym.gym_app.service.dto.SucursalFormDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/sucursales")
@RequiredArgsConstructor
public class SucursalesRestController {

    private final SucursalService sucursalService;

    @GetMapping
    public List<SucursalDTO> listar() {
        return sucursalService.listar();
    }

    @GetMapping("/activas")
    public List<SucursalDTO> listarActivas() {
        return sucursalService.listarActivas();
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> crear(@Valid @RequestBody SucursalFormDTO dto) {
        SucursalDTO creada = sucursalService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
