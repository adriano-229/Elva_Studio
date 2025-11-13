package com.example.biblioteca.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.biblioteca.entities.Base;

// hacemos que el id sea generico para poder reutilizarlo
public interface BaseService<E extends Base, ID extends Serializable> {
	
	public List<E> findAll() throws Exception;
	
	//public List<E> findAllByIds(Iterable<ID> ids) throws Exception;
	
	public Page<E> findAll(Pageable pageable) throws Exception;
	
	public E findByid(ID id) throws Exception;
	
	public E save(E entity) throws Exception;
	
	public E update(ID id, E entity) throws Exception;
	
	public boolean delete(ID id) throws Exception;
	
	
}
