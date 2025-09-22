package com.projects.gym.gym_app.service.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnvioMensajeCommand {
    @NotNull
    private Long socioId; // envío puntual a 1 socio
}
