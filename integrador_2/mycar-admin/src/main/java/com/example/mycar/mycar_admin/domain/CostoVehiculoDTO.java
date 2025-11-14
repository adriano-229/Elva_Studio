package com.example.mycar.mycar_admin.domain;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CostoVehiculoDTO extends BaseDTO {

    @NotNull(message = "La fecha desde no puede ser nula")
    @PastOrPresent(message = "La fecha desde no puede ser futura")
    private LocalDate fechaDesde;

    @NotNull(message = "La fecha hasta no puede ser nula")
    @Future(message = "La fecha hasta debe ser una fecha futura")
    private LocalDate fechaHasta;

    @Positive(message = "El costo debe ser un valor positivo")
    private BigDecimal costo;

}
