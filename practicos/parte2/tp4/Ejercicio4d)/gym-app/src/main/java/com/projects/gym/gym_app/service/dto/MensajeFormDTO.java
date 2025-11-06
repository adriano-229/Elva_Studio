package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.TipoMensaje;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MensajeFormDTO {
    private String id;

    @NotBlank
    private String titulo;

    @NotBlank
    private String texto;

    @NotNull
    private TipoMensaje tipoMensaje;

    private String autorNombre;
    private LocalDate fechaEnvioPromocion;
    private Long cantidadSociosEnviados;
}
