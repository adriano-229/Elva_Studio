package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.domain.dto.LocalidadDTO;

public interface LocalidadService extends BaseService<LocalidadDTO, Long> {

	List<LocalidadDTO> searchByDenominacion(String denominacion) throws Exception;
	

}
