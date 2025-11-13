package com.example.mycar.dto.correos;

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
