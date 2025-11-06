package com.example.biblioteca.strategy;

import java.util.List;

import com.example.biblioteca.entities.Libro;

public interface BusquedaLibroStrategy {
	
	List<Libro> buscar(String valor);
	


}
