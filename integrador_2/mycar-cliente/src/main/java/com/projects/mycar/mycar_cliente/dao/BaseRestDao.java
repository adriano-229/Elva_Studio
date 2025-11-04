package com.projects.mycar.mycar_cliente.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.projects.mycar.mycar_cliente.domain.BaseDTO;


public interface BaseRestDao<E extends BaseDTO, ID extends Serializable>{
	
	public void crear(E dto) throws Exception;
	
	public void actualizar(E dto) throws Exception;
	
	public void eliminar(ID id) throws Exception;
	
	public boolean existsById(ID id) throws Exception;
	
	public Optional<E> buscarPorId(ID id) throws Exception;
	
	public List<E> listarActivo() throws Exception;

}
