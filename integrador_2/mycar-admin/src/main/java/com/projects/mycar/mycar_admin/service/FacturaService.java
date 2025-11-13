package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.FacturaDTO;

import java.util.List;

public interface FacturaService extends BaseService<FacturaDTO, Long> {

    List<FacturaDTO> obtenerPagosPendientes() throws Exception;

    FacturaDTO aprobarPago(Long idFactura) throws Exception;

    FacturaDTO anularPago(Long idFactura, String motivo) throws Exception;


}
