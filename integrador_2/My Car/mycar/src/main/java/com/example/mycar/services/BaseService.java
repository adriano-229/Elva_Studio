package com.example.mycar.services;

import java.io.Serializable;
import java.util.List;

import com.example.mycar.dto.BaseDTO;

public interface BaseService<D extends BaseDTO, ID extends Serializable> {
	
	public List<D> findAll() throws Exception;
	
	public List<D> findAllByIds(Iterable<ID> ids) throws Exception;

	//public Page<E> findAll(Pageable pageable) throws Exception;
	
	D findById(ID id) throws Exception;

	public D save(D entityDTO) throws Exception;
	
	public D update(ID id, D entityDTO) throws Exception;
	
	public boolean delete(ID id) throws Exception;

}
