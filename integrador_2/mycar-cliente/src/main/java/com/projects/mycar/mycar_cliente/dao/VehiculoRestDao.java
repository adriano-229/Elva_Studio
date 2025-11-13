package com.projects.mycar.mycar_cliente.dao;

import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

import java.util.List;

public interface VehiculoRestDao extends BaseRestDao<VehiculoDTO, Long> {
    List<VehiculoDTO> findByEstado(EstadoVehiculo estado) throws Exception;
}
