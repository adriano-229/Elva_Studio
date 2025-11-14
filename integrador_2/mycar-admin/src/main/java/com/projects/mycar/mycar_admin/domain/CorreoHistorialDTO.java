package com.projects.mycar.mycar_admin.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CorreoHistorialDTO extends BaseDTO {

    private String destinatario;
    private String asunto;
    private String cuerpo;
    private LocalDateTime fechaEnvio;
    private Boolean exito;
    private String mensajeError;

}
