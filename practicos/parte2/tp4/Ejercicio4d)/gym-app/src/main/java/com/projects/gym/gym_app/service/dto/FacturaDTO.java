package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.EstadoFactura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record FacturaDTO(
    String id,
    long numeroFactura,
    LocalDate fechaFactura,
    BigDecimal totalPagado,
    EstadoFactura estado,
    Long socioId,
    String socioNombre,
    String formaDePagoId,
    String formaDePagoTexto,
    List<DetalleFacturaDTO> detalles
) {}