package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.ReportesRestDao;
import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteRecaudacionModeloDTO;
import com.projects.mycar.mycar_admin.service.ReportesService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportesServiceImpl extends BaseServiceImpl<ReporteRecaudacionModeloDTO, Long> implements ReportesService {

    private final ReportesRestDao reportesRestDao;

    public ReportesServiceImpl(ReportesRestDao reportesRestDao) {
        super(reportesRestDao);
        this.reportesRestDao = reportesRestDao;
    }

    @Override
    public List<ReporteRecaudacionModeloDTO> obtenerRecaudacionPorModelo(LocalDate desde, LocalDate hasta)
            throws Exception {
        validarFechas(desde, hasta);
        return reportesRestDao.obtenerRecaudacionPorModelo(desde, hasta);
    }

    @Override
    public ReportFileDTO descargarRecaudacionPorModelo(LocalDate desde, LocalDate hasta, String formato)
            throws Exception {
        validarFechas(desde, hasta);
        return reportesRestDao.descargarRecaudacionPorModelo(desde, hasta, formato);
    }

    private void validarFechas(LocalDate desde, LocalDate hasta) throws Exception {
        if (desde == null || hasta == null) {
            throw new Exception("Debe indicar el período a consultar");
        }
        if (desde.isAfter(hasta)) {
            throw new Exception("La fecha inicial no puede ser posterior a la final");
        }
    }

    @Override
    protected void validar(ReporteRecaudacionModeloDTO entity) throws Exception {
        // No se realizan validaciones para creación/actualización en reportes
    }

    @Override
    protected void beforeSave(ReporteRecaudacionModeloDTO entity) throws Exception {
        // No se requiere lógica de guardado
    }
}
