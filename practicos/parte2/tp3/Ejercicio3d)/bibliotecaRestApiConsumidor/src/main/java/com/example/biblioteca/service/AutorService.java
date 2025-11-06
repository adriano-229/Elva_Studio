package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.domain.dto.AutorDTO;

public interface AutorService extends BaseService<AutorDTO, Long>{

	List<AutorDTO> searchByNombreApellido(String filtro) throws Exception;

}
