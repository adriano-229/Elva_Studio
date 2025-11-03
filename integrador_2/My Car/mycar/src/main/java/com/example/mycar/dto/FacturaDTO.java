package com.example.mycar.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.example.mycar.enums.EstadoFactura;

public record FacturaDTO(
    String id,
    long numeroFactura,
    LocalDate fechaFactura,
    BigDecimal totalPagado,
    EstadoFactura estado,
    String formaDePagoId,
    String formaDePagoTexto,
    List<DetalleFacturaDTO> detalles
) {}