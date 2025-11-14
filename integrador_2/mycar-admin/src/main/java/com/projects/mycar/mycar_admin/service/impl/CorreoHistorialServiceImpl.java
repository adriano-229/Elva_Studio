package com.projects.mycar.mycar_admin.service.impl;

import com.projects.mycar.mycar_admin.dao.CorreoHistorialRestDao;
import com.projects.mycar.mycar_admin.domain.CorreoHistorialDTO;
import com.projects.mycar.mycar_admin.domain.PageResponse;
import com.projects.mycar.mycar_admin.service.CorreoHistorialService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CorreoHistorialServiceImpl implements CorreoHistorialService {

    private final CorreoHistorialRestDao restDao;

    public CorreoHistorialServiceImpl(CorreoHistorialRestDao restDao) {
        this.restDao = restDao;
    }

    @Override
    public PageResponse<CorreoHistorialDTO> buscar(String destinatario,
                                                   Boolean exito,
                                                   LocalDate fechaDesde,
                                                   LocalDate fechaHasta,
                                                   int page,
                                                   int size) throws Exception {
        return restDao.buscar(destinatario, exito, fechaDesde, fechaHasta, page, size);
    }
}
