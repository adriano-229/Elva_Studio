package com.projects.gym.gym_app.controller.rest;

import com.projects.gym.gym_app.service.ValorCuotaService;
import com.projects.gym.gym_app.service.dto.ValorCuotaDTO;
import com.projects.gym.gym_app.service.dto.ValorCuotaFormDTO;
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
@RequestMapping("/api/admin/valor-cuota")
@RequiredArgsConstructor
public class ValorCuotaRestController {

    private final ValorCuotaService valorCuotaService;

    @GetMapping
    public List<ValorCuotaDTO> listar() {
        return valorCuotaService.listar();
    }

    @PostMapping
    public ResponseEntity<ValorCuotaDTO> crear(@Valid @RequestBody ValorCuotaFormDTO dto) {
        ValorCuotaDTO creada = valorCuotaService.crear(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }
}
