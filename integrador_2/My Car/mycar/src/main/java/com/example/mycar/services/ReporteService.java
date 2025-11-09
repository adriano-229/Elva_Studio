package com.example.mycar.services;

import java.time.LocalDate;
import java.util.List;

import com.example.mycar.dto.reportes.AlquilerReporteDTO;
import com.example.mycar.dto.reportes.RecaudacionModeloDTO;
import com.example.mycar.enums.ReportFormat;

public interface ReporteService {

    List<AlquilerReporteDTO> obtenerAlquileresPorPeriodo(LocalDate desde, LocalDate hasta);

    byte[] exportarAlquileresPorPeriodo(LocalDate desde, LocalDate hasta, ReportFormat formato);

    List<RecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta);

    byte[] exportarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, ReportFormat formato);
}
