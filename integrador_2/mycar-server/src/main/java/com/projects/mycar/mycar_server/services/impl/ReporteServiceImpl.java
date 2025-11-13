package com.projects.mycar.mycar_server.services.impl;

import com.projects.mycar.mycar_server.dto.reportes.AlquilerReporteDTO;
import com.projects.mycar.mycar_server.dto.reportes.RecaudacionModeloDTO;
import com.projects.mycar.mycar_server.enums.ReportFormat;
import com.projects.mycar.mycar_server.repositories.AlquilerRepository;
import com.projects.mycar.mycar_server.services.ReporteService;
import com.projects.mycar.mycar_server.services.export.AlquilerReportExporter;
import com.projects.mycar.mycar_server.services.export.RecaudacionModeloReportExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReporteServiceImpl implements ReporteService {

    private final AlquilerRepository alquilerRepository;
    private final AlquilerReportExporter alquilerReportExporter;
    private final RecaudacionModeloReportExporter recaudacionModeloReportExporter;

    @Override
    public List<AlquilerReporteDTO> obtenerAlquileresPorPeriodo(LocalDate desde, LocalDate hasta) {
        /*Date desdeDate = desde == null ? null : Date.valueOf(desde);
        Date hastaDate = hasta == null ? null : Date.valueOf(hasta);*/
        return alquilerRepository.findAlquileresPorPeriodo(desde, hasta);
    }

    @Override
    public byte[] exportarAlquileresPorPeriodo(LocalDate desde, LocalDate hasta, ReportFormat formato) {
        List<AlquilerReporteDTO> datos = obtenerAlquileresPorPeriodo(desde, hasta);
        return alquilerReportExporter.export(datos, formato);
    }

    @Override
    public List<RecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta) {
        return alquilerRepository.calcularRecaudacionPorModelo(desde, hasta);
    }

    @Override
    public byte[] exportarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, ReportFormat formato) {
        List<RecaudacionModeloDTO> datos = obtenerRecaudacionPorModelo(desde, hasta);
        return recaudacionModeloReportExporter.export(datos, formato);
    }
}
