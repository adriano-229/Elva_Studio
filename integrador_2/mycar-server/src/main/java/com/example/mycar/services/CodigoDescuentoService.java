package com.example.mycar.services;

import com.example.mycar.dto.CodigoDescuentoDTO;

public interface CodigoDescuentoService extends BaseService<CodigoDescuentoDTO, Long> {

    CodigoDescuentoDTO findByCodigo(String codigo) throws Exception;

    boolean existsByCodigo(String codigo) throws Exception;
}
