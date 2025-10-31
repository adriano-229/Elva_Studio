package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.domain.dto.LibroDTO;

public interface LibroService extends BaseService<LibroDTO, Long>{
	
	List<LibroDTO> buscarPorFiltro(String filtro) throws Exception;
	List<LibroDTO> buscarPorAutor(String filtro) throws Exception;
	List<LibroDTO> buscarPorGenero(String genero) throws Exception;
	List<LibroDTO> buscarPorTitulo(String titulo) throws Exception;
}
