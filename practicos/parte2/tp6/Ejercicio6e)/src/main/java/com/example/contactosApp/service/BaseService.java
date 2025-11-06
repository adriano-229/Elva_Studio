package com.example.contactosApp.service;

import java.io.Serializable;
import java.util.List;

import com.example.contactosApp.domain.dto.BaseDTO;

public interface BaseService<D extends BaseDTO, ID extends Serializable> {
	
	public D save(D entityDTO) throws Exception;
	public D update(ID id, D entityDTO) throws Exception;
	public void delete(ID id) throws Exception;
	public D findById(ID id) throws Exception;
	public List<D> findAll() throws Exception;

}
