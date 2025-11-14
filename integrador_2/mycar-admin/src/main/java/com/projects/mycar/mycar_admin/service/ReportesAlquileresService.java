package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteAlquilerDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportesAlquileresService extends BaseService<ReporteAlquilerDTO, Long> {

    List<ReporteAlquilerDTO> obtenerAlquileres(LocalDate desde, LocalDate hasta) throws Exception;

    ReportFileDTO descargarAlquileres(LocalDate desde, LocalDate hasta, String formato) throws Exception;

}
