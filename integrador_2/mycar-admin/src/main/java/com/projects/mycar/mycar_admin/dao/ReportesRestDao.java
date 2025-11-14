package com.projects.mycar.mycar_admin.dao;

import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteRecaudacionModeloDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReportesRestDao extends BaseRestDao<ReporteRecaudacionModeloDTO, Long> {

    List<ReporteRecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta) throws Exception;

    ReportFileDTO descargarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, String formato) throws Exception;

}
