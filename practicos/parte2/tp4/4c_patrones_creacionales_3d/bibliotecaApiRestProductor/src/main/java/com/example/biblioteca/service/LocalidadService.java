package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.entities.Localidad;

public interface LocalidadService extends BaseService<Localidad, Long> {

	List<Localidad> searchByDenominacion(String denominacion) throws Exception;

}
