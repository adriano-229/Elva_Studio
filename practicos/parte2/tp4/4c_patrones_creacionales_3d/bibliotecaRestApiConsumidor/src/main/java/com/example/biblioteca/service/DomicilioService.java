package com.example.biblioteca.service;

import com.example.biblioteca.domain.dto.DomicilioDTO;

public interface DomicilioService extends BaseService<DomicilioDTO, Long>{
	
	DomicilioDTO buscarPorCalleNroYLocalidad(String calle, int numero, String denominacion) throws Exception;

}
