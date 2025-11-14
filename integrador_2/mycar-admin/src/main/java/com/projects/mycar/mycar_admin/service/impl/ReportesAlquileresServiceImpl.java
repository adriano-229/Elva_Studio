package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.ReportesAlquileresRestDao;
import com.projects.mycar.mycar_admin.domain.ReportFileDTO;
import com.projects.mycar.mycar_admin.domain.ReporteAlquilerDTO;
import com.projects.mycar.mycar_admin.service.ReportesAlquileresService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReportesAlquileresServiceImpl extends BaseServiceImpl<ReporteAlquilerDTO, Long>
        implements ReportesAlquileresService {

    private final ReportesAlquileresRestDao reportesAlquileresRestDao;

    public ReportesAlquileresServiceImpl(ReportesAlquileresRestDao reportesAlquileresRestDao) {
        super(reportesAlquileresRestDao);
        this.reportesAlquileresRestDao = reportesAlquileresRestDao;
    }

    @Override
    public List<ReporteAlquilerDTO> obtenerAlquileres(LocalDate desde, LocalDate hasta) throws Exception {
        validarFechas(desde, hasta);
        return reportesAlquileresRestDao.obtenerAlquileres(desde, hasta);
    }

    @Override
    public ReportFileDTO descargarAlquileres(LocalDate desde, LocalDate hasta, String formato) throws Exception {
        validarFechas(desde, hasta);
        return reportesAlquileresRestDao.descargarAlquileres(desde, hasta, formato);
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
    protected void validar(ReporteAlquilerDTO entity) throws Exception {
        // sin validaciones extras
    }

    @Override
    protected void beforeSave(ReporteAlquilerDTO entity) throws Exception {
        // no se requiere lógica previa
    }
}
