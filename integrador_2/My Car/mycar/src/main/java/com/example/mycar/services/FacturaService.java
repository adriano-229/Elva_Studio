package com.example.mycar.services;

import com.example.mycar.dto.FacturaDTO;

public interface FacturaService extends BaseService<FacturaDTO, Long> {

    Long generarNumeroFactura() throws Exception;
}

