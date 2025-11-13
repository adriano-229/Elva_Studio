package com.projects.mycar.mycar_admin.dao;

import com.example.mycar.mycar_admin.domain.FacturaDTO;

import java.util.List;

public interface FacturaRestDao extends BaseRestDao<FacturaDTO, Long> {

    List<FacturaDTO> obtenerPagosPendientes() throws Exception;

    FacturaDTO aprobarPago(Long idFactura) throws Exception;

    FacturaDTO anularPago(Long idFactura, String motivo) throws Exception;

}
