package com.example.biblioteca.service;
import java.io.Serializable;
import java.util.List;

import com.example.biblioteca.domain.dto.BaseDTO;

/*import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;*/

// hacemos que el id sea generico para poder reutilizarlo
public interface BaseService<E extends BaseDTO, ID extends Serializable> {
	
	public List<E> findAll() throws Exception;
	
	public E findById(ID id) throws Exception;
	
	public void save(E entity) throws Exception;
	
	public void update(ID id, E entity) throws Exception;
	
	public void delete(ID id) throws Exception;
	
	
	
	
}
