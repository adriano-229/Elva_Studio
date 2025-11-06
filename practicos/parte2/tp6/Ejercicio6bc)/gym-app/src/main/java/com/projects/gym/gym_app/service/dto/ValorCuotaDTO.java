package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.Mes;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ValorCuotaDTO {
    String id;
    Mes mes;
    Integer anio;
    BigDecimal valor;
    LocalDateTime creadoEl;
}
