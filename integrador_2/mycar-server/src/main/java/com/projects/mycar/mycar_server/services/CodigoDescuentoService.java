package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.CodigoDescuentoDTO;

public interface CodigoDescuentoService extends BaseService<CodigoDescuentoDTO, Long> {

    CodigoDescuentoDTO findByCodigo(String codigo) throws Exception;

    boolean existsByCodigo(String codigo) throws Exception;
}
