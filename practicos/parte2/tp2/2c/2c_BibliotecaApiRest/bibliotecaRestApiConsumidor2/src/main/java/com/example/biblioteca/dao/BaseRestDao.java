package com.example.biblioteca.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.example.biblioteca.domain.dto.BaseDTO;

//Esta anotacion contorla que no se puedan crear instancias de esta interface

public interface BaseRestDao<E extends BaseDTO, ID extends Serializable>{
	
	public void crear(E dto) throws Exception;
	
	public void actualizar(E dto) throws Exception;
	
	public void eliminar(ID id) throws Exception;
	
	public boolean existsById(ID id) throws Exception;
	
	public Optional<E> buscarPorId(ID id) throws Exception;
	
	public List<E> listarActivo() throws Exception;

}
