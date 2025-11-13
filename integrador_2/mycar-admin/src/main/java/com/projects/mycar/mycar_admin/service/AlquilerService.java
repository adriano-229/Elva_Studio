package com.projects.mycar.mycar_admin.service;

import com.projects.mycar.mycar_admin.domain.AlquilerDTO;

import java.time.LocalDate;
import java.util.List;

public interface AlquilerService extends BaseService<AlquilerDTO, Long> {

    List<AlquilerDTO> buscarPorPeriodo(LocalDate desde, LocalDate hasta) throws Exception;

    List<AlquilerDTO> buscarPorCliente(Long idCliente) throws Exception;
}
