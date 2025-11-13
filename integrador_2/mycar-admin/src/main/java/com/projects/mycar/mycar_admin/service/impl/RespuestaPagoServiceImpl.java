package com.projects.mycar.mycar_admin.service.impl;

import com.example.mycar.mycar_admin.domain.RespuestaPagoDTO;
import com.example.mycar.mycar_admin.domain.SolicitudPagoDTO;
import com.projects.mycar.mycar_admin.dao.impl.RespuestaPagoRestDaoImpl;
import com.projects.mycar.mycar_admin.service.RespuestaPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RespuestaPagoServiceImpl implements RespuestaPagoService {

    @Autowired
    private RespuestaPagoRestDaoImpl daoRespuestaPago;

    @Override
    public RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) throws Exception {
        try {
            return daoRespuestaPago.procesarPago(solicitud);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
