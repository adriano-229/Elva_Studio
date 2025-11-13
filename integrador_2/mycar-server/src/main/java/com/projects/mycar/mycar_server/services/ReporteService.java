package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.reportes.AlquilerReporteDTO;
import com.projects.mycar.mycar_server.dto.reportes.RecaudacionModeloDTO;
import com.projects.mycar.mycar_server.enums.ReportFormat;

import java.time.LocalDate;
import java.util.List;

public interface ReporteService {

    List<AlquilerReporteDTO> obtenerAlquileresPorPeriodo(LocalDate desde, LocalDate hasta);

    byte[] exportarAlquileresPorPeriodo(LocalDate desde, LocalDate hasta, ReportFormat formato);

    List<RecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta);

    byte[] exportarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, ReportFormat formato);
}
