package com.example.mycar.dto;

import java.math.BigDecimal;

import com.example.mycar.enums.Mes;

public record DetalleFacturaDTO(
    String cuotaId,
    Mes mes,       // o Enum/number, ajusta a tu modelo
    Integer anio,
    BigDecimal importe
) {}