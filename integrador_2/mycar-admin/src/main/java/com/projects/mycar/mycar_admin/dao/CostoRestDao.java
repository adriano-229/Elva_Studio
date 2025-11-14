package com.projects.mycar.mycar_admin.dao;

import java.util.List;

import com.example.mycar.mycar_admin.domain.AlquilerFormDTO;
import com.example.mycar.mycar_admin.domain.CostoVehiculoDTO;
import com.example.mycar.mycar_admin.domain.PagareDTO;

public interface CostoRestDao extends BaseRestDao<CostoVehiculoDTO, Long> {
	
	PagareDTO calcularPagare(List<Long> alquilerIds, Long clienteId) throws Exception;
	AlquilerFormDTO calcularCostoAlquiler(AlquilerFormDTO alquiler) throws Exception;

}
