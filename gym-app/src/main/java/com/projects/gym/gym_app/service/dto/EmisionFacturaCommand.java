package com.projects.gym.gym_app.service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class EmisionFacturaCommand {
    @NotNull
    private Long socioId;

    @NotEmpty
    private List<String> cuotasIds;

    // Si marcás pagada en el acto, forma de pago requerida
    private String formaDePagoId;
    private boolean marcarPagada;
    private String observacionPago;
}
