package com.projects.mycar.mycar_admin.domain;

import lombok.*;

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
