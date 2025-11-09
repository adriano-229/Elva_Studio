package com.example.mycar.services;

import java.time.LocalDate;
import java.util.List;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.enums.EstadoVehiculo;

public interface VehiculoService extends BaseService<VehiculoDTO, Long>{
	List<VehiculoDTO> buscarVehiculosPorEstadoYPeriodo(EstadoVehiculo estado,
												        LocalDate fechaDesde,
												        LocalDate fechaHasta);
	
	List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado);
}
