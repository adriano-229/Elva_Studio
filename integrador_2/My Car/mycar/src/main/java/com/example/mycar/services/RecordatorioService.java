package com.example.mycar.services;

import java.time.LocalDate;

import com.example.mycar.dto.recordatorios.RecordatorioJobResponse;

public interface RecordatorioService {

    RecordatorioJobResponse enviarRecordatoriosProgramados(LocalDate fechaObjetivo);

    RecordatorioJobResponse enviarRecordatoriosDevolucion(Long clienteId);

    void enviarPrueba(Long clienteId);
}
