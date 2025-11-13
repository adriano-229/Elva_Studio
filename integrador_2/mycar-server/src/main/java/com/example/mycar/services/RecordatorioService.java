package com.example.mycar.services;

import com.example.mycar.dto.recordatorios.RecordatorioJobResponse;

import java.time.LocalDate;

public interface RecordatorioService {

    RecordatorioJobResponse enviarRecordatoriosProgramados(LocalDate fechaObjetivo);

    RecordatorioJobResponse enviarRecordatoriosDevolucion(Long clienteId);

    void enviarPrueba(Long clienteId);
}
