package com.example.biblioteca.dao;

import java.util.List;

import com.example.biblioteca.domain.dto.LibroDTO;

public interface LibroRestDao extends BaseRestDao<LibroDTO, Long> {
	
	List<LibroDTO> buscarPorFiltro(String filtro) throws Exception;
	List<LibroDTO> buscarPorAutor(String filtro) throws Exception;
	List<LibroDTO> buscarPorGenero(String genero) throws Exception;
	LibroDTO buscarPorTitulo(String titulo) throws Exception;
	
}
