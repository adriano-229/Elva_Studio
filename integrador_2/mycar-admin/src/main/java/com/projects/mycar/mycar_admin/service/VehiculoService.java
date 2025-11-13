package com.projects.mycar.mycar_admin.service;

import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;

import java.time.LocalDate;
import java.util.List;

public interface VehiculoService extends BaseService<VehiculoDTO, Long> {

    List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta)
            throws Exception;

    List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception;

}
