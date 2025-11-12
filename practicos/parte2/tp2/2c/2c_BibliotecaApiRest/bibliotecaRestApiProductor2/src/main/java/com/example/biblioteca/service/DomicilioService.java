package com.example.biblioteca.service;

import java.util.Optional;

import com.example.biblioteca.entities.Domicilio;

public interface DomicilioService extends BaseService<Domicilio, Long>{
	
	Optional<Domicilio> buscarPorCalleNumeroYLocalidad(String calle, int numero, String denominacion) throws Exception;

}
