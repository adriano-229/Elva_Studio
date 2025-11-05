package com.example.mycar.services;

import java.io.Serializable;
import java.util.List;

import com.example.mycar.entities.Base;

public interface BaseService<E extends Base, ID extends Serializable> {
	
	public List<E> findAll() throws Exception;
	
	public List<E> findAllByIds(Iterable<ID> ids) throws Exception;
	
	public E findByid(ID id) throws Exception;
	
	public E save(E entity) throws Exception;
	
	public E update(ID id, E entity) throws Exception;
	
	public boolean delete(ID id) throws Exception;
	
	
}
