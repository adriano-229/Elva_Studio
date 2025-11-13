package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.FacturaDTO;

public interface FacturaService extends BaseService<FacturaDTO, Long> {

    Long generarNumeroFactura() throws Exception;
}

