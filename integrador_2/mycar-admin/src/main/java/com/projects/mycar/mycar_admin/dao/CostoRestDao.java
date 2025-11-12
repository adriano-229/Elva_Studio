package com.projects.mycar.mycar_admin.dao;

import java.util.List;

import com.example.mycar.mycar_admin.domain.PagareDTO;

public interface CostoRestDao {
	
	PagareDTO calcularPagare(List<Long> alquilerIds, Long clienteId) throws Exception;
	String calcularCostoAlquiler(Long alquilerId) throws Exception;

}
