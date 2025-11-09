package com.example.mycar.services;

import java.util.List;
import java.util.Optional;

import com.example.mycar.dto.LocalidadDTO;

public interface LocalidadService extends BaseService<LocalidadDTO, Long> {
	
	List<LocalidadDTO> findByDepartamentoId(Long departamentoId);
	List<LocalidadDTO> findByDepartamentoNombre(String nombre);
	Optional<LocalidadDTO> findByNombreYDepartamento(String nombre, Long departamentoId);

}
