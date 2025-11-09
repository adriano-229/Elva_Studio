package com.example.mycar.dto.recordatorios;

import java.time.LocalDate;
import java.util.List;

/**
 * Resumen del job de recordatorios.
 */
public record RecordatorioJobResponse(
        LocalDate fechaObjetivo,
        int totalClientes,
        int totalEnviados,
        List<RecordatorioDestinatarioResponse> destinatarios
) {
}
