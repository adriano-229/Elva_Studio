package com.example.contactosApp.service;

import com.example.contactosApp.domain.dto.EmpresaDTO;

public interface EmpresaService extends BaseService<EmpresaDTO, Long>{
	
	EmpresaDTO findByNombre(String nombre) throws Exception;
	boolean existsByNombre(String nombre) throws Exception;

}
