package com.example.biblioteca.service;

import java.util.List;

import com.example.biblioteca.entities.Autor;

public interface AutorService extends BaseService<Autor, Long>{
	
	List<Autor> searchByNombreApellido(String filtro) throws Exception;

}
