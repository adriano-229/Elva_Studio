package com.projects.mycar.mycar_admin.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.example.mycar.mycar_admin.domain.VehiculoDTO;
import com.example.mycar.mycar_admin.domain.enums.EstadoVehiculo;

public interface VehiculoRestDao extends BaseRestDao<VehiculoDTO, Long>{
	
	List<VehiculoDTO> buscarPorEstadoYPeriodo(EstadoVehiculo estado, LocalDate desde, LocalDate hasta) throws Exception;
	List<VehiculoDTO> buscarPorEstado(EstadoVehiculo estado) throws Exception;
	VehiculoDTO buscarPorPatente(String patente) throws Exception;


}
