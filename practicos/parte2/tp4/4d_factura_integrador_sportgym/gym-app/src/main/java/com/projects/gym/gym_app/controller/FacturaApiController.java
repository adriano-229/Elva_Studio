package com.projects.gym.gym_app.controller;

import com.projects.gym.gym_app.service.FacturaService;
import com.projects.gym.gym_app.service.dto.CuotaPendienteDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@RequiredArgsConstructor
public class FacturaApiController {

    private final FacturaService facturaService;

    @GetMapping("/cuotas-pendientes")
    public List<CuotaPendienteDTO> cuotasPendientes(@RequestParam Long socioId) {
        return facturaService.cuotasPendientes(socioId);
    }
}
