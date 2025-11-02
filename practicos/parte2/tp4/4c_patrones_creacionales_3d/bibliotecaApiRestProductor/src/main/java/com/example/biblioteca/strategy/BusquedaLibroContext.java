package com.example.biblioteca.strategy;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Libro;

@Service
public class BusquedaLibroContext {
	
	@Autowired
	private Map<String, BusquedaLibroStrategy> estrategias;
	
    public List<Libro> buscar(String tipo, String filtro){
    	BusquedaLibroStrategy estrategia = estrategias.get(tipo);
    	if (estrategia == null) {
    		throw new IllegalArgumentException("Tipo de busqueda no válido: " + tipo);
    	}
    	return estrategia.buscar(filtro);
    }
}
