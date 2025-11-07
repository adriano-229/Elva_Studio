package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.Mes;

import java.math.BigDecimal;

public record CuotaPendienteDTO(
        String id,
        Mes mes,
        Integer anio,
        BigDecimal importe
) {}
