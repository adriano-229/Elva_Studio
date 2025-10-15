package com.example.biblioteca.dao;

import java.util.List;

import com.example.biblioteca.domain.dto.AutorDTO;

public interface AutorRestDao extends BaseRestDao<AutorDTO, Long>{
	
	List<AutorDTO> buscarPorNombreApellido(String fitro) throws Exception;

}
