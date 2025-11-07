package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.Mes;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ValorCuotaFormDTO {

    @NotNull
    private Mes mes;

    @NotNull
    private Integer anio;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor a cero")
    private BigDecimal valor;
}
