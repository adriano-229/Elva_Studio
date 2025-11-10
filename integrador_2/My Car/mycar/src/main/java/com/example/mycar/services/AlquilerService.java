package com.example.mycar.services;

import com.example.mycar.dto.AlquilerDTO;

import java.time.LocalDate;
import java.util.List;

public interface AlquilerService extends BaseService<AlquilerDTO, Long> {

    /**
     * Busca un alquiler que no tenga factura asociada (pendiente de pago).
     */
    AlquilerDTO findByIdSinFactura(Long id) throws Exception;

    List<AlquilerDTO> searchPorPeriodo(LocalDate desde, LocalDate hasta) throws Exception;

    List<AlquilerDTO> searchByCliente(Long idCliente) throws Exception;
}

