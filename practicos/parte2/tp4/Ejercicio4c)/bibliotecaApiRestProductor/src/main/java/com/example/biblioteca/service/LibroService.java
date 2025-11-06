package com.example.biblioteca.service;

import java.util.List;
import java.util.Optional;

import com.example.biblioteca.entities.Libro;

public interface LibroService extends BaseService<Libro, Long>{
	
	List<Libro> buscar(String filtro) throws Exception;
	List<Libro> buscarPorAutor(String filtro) throws Exception;
	List<Libro> buscarPorTituloOGenero(String filtro) throws Exception;
	Optional<Libro> buscarPorTitulo(String titulo) throws Exception;

}
