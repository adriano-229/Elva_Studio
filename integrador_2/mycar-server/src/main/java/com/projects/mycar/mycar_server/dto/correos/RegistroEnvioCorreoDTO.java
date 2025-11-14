package com.projects.mycar.mycar_server.dto.correos;

import java.time.LocalDateTime;

public record RegistroEnvioCorreoDTO(
        Long id,
        String destinatario,
        String asunto,
        String cuerpo,
        LocalDateTime fechaEnvio,
        Boolean exito,
        String mensajeError
) {
}
