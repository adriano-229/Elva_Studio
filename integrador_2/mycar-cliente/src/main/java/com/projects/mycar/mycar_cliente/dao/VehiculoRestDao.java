package com.projects.mycar.mycar_cliente.dao;

import java.util.List;

import com.projects.mycar.mycar_cliente.domain.VehiculoDTO;
import com.projects.mycar.mycar_cliente.domain.enums.EstadoVehiculo;

public interface VehiculoRestDao extends BaseRestDao<VehiculoDTO, Long> {
	List<VehiculoDTO> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado) throws Exception;
}
