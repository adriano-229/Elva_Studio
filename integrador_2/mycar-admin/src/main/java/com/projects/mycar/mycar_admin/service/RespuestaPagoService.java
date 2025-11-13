package com.projects.mycar.mycar_admin.service;

import com.example.mycar.mycar_admin.domain.RespuestaPagoDTO;
import com.example.mycar.mycar_admin.domain.SolicitudPagoDTO;

public interface RespuestaPagoService {

    RespuestaPagoDTO procesarPago(SolicitudPagoDTO solicitud) throws Exception;

}
