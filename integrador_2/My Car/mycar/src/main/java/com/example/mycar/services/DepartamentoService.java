package com.example.mycar.services;

import java.util.List;

import com.example.mycar.dto.DepartamentoDTO;

public interface DepartamentoService extends BaseService<DepartamentoDTO, Long> {
	
	List<DepartamentoDTO> findAllByOrderByNombreAsc();
	List<DepartamentoDTO> findAllActivosByProvinciaIdOrderByNombre(Long provinciaId);
	List<DepartamentoDTO> findAllActivosByProvinciaNombreOrderByNombre(String provinciaNombre);

}
