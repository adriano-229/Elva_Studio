package com.example.biblioteca.dao;

import com.example.biblioteca.domain.dto.DomicilioDTO;

public interface DomicilioRestDao extends BaseRestDao<DomicilioDTO, Long>{
	
	DomicilioDTO buscarPorCalleNumeroYLocalidad(String calle, int numero, String denominacion) throws Exception;

}
