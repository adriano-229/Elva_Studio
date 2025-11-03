package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.service.EmpresaService;
import com.projects.gym.gym_app.service.dto.EmpresaDTO;
import com.projects.gym.gym_app.service.dto.EmpresaFormDTO;
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
@RequestMapping("/api/admin/empresas")
@RequiredArgsConstructor
public class EmpresasRestController {

    private final EmpresaService empresaService;

    @GetMapping
    public List<EmpresaDTO> listar() {
        return empresaService.listar();
    }

    @PostMapping
    public ResponseEntity<EmpresaDTO> crear(@Valid @RequestBody EmpresaFormDTO dto) {
        EmpresaDTO creada = empresaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
