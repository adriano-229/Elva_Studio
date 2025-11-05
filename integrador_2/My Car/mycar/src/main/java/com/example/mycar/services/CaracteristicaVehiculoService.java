package com.example.mycar.services;

import java.util.List;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.enums.EstadoVehiculo;

public interface CaracteristicaVehiculoService extends BaseService<CaracteristicaVehiculoDTO, Long>{
	
	public List<CaracteristicaVehiculoDTO> findByEstadoVehiculo(EstadoVehiculo estado) throws Exception;

}
