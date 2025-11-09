package com.example.mycar.dto.recordatorios;

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
