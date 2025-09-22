package com.projects.gym.gym_app.service.dto;

import java.math.BigDecimal;

public record DetalleFacturaDTO(
    String cuotaId,
    String mes,       // o Enum/number, ajusta a tu modelo
    Integer anio,
    BigDecimal importe
) {}