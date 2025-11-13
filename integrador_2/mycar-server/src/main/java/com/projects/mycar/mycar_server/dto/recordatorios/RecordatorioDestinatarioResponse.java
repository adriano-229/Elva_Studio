package com.projects.mycar.mycar_server.dto.recordatorios;

/**
 * Resultado por destinatario luego de ejecutar el job.
 */
public record RecordatorioDestinatarioResponse(
        Long clienteId,
        String email,
        String asunto,
        boolean enviado,
        String detalle
) {
}
