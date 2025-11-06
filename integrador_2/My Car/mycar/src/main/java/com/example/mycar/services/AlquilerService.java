package com.example.mycar.services;

import com.example.mycar.dto.AlquilerDTO;

public interface AlquilerService extends BaseService<AlquilerDTO, Long> {

    /**
     * Busca un alquiler que no tenga factura asociada (pendiente de pago).
     */
    AlquilerDTO findByIdSinFactura(Long id) throws Exception;
}

