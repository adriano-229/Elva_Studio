package com.example.mycar.services;

import java.util.List;

import com.example.mycar.dto.ProvinciaDTO;

public interface ProvinciaService extends BaseService<ProvinciaDTO, Long>{
	
	List<ProvinciaDTO> findAllActivasOrderByNombreAsc();
	List<ProvinciaDTO> findAllActivasByPaisIdOrderByNombreAsc(Long paisId);
	List<ProvinciaDTO> findAllActivasByPaisNombreOrderByNombreAsc(String paisNombre);
	
	
}
