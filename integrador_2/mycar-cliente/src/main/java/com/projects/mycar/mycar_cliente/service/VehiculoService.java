package com.projects.mycar.mycar_cliente.service;


import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

import java.util.List;

public interface VehiculoService extends BaseService<VehiculoDTO, Long> {

    List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado);
}
