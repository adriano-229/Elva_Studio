package com.example.mycar.services;

import java.util.List;

import com.example.mycar.dto.PaisDTO;

public interface PaisService extends BaseService<PaisDTO, Long> {
	List<PaisDTO> findAllByOrderByNombreAsc();
	List<PaisDTO> findAllActivosOrderByNombreAsc();
	
}
