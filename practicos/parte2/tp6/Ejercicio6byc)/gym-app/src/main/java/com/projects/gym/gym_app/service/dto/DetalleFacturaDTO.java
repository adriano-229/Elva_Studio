package com.projects.gym.gym_app.service.dto;

import java.math.BigDecimal;
import com.projects.gym.gym_app.domain.enums.Mes;

public record DetalleFacturaDTO(
    String cuotaId,
    Mes mes,       // o Enum/number, ajusta a tu modelo
    Integer anio,
    BigDecimal importe
) {}