package com.projects.mycar.mycar_cliente.service;


import java.util.List;


import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;

import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

public interface VehiculoService extends BaseService<VehiculoDTO, Long> {
	
    List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado);
}
