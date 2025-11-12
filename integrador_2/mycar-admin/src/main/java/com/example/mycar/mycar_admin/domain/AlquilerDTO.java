package com.example.mycar.mycar_admin.domain;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AlquilerDTO extends BaseDTO {

    @NotNull(message = "La fecha desde no puede ser nula")
    @FutureOrPresent(message = "La fecha desde debe ser hoy o una fecha futura")
    private LocalDate fechaDesde;

    @NotNull(message = "La fecha hasta no puede ser nula")
    @FutureOrPresent(message = "La fecha hasta debe ser una fecha actual o futura")
    private LocalDate fechaHasta;

    @NotNull(message = "La documentación es obligatoria")
    private DocumentacionDTO documentacion;

    @NotNull(message = "El vehículo es obligatorio")
    private Long vehiculoId;

    private VehiculoDTO vehiculo;

    @NotNull(message = "El cliente es obligatorio")
    private Long clienteId;

    private ClienteDTO cliente;

    @NotNull(message = "El costo calculado no puede ser nulo")
    @Positive(message = "El costo calculado debe ser mayor que 0")
    private Double costoCalculado;

    @NotNull(message = "La cantidad de días es obligatoria")
    @Positive(message = "La cantidad de días debe ser mayor que 0")
    private Integer cantidadDias;
}
