package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.recordatorios.RecordatorioJobResponse;

import java.time.LocalDate;

public interface RecordatorioService {

    RecordatorioJobResponse enviarRecordatoriosProgramados(LocalDate fechaObjetivo);

    RecordatorioJobResponse enviarRecordatoriosDevolucion(Long clienteId);

    void enviarPrueba(Long clienteId);
}
