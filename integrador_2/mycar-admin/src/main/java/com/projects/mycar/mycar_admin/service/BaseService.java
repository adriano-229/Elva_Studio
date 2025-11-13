package com.projects.mycar.mycar_admin.service;


import com.projects.mycar.mycar_admin.domain.BaseDTO;

import java.io.Serializable;
import java.util.List;

// hacemos que el id sea generico para poder reutilizarlo
public interface BaseService<E extends BaseDTO, ID extends Serializable> {

    List<E> findAll() throws Exception;

    E findById(ID id) throws Exception;

    void save(E entity) throws Exception;

    void update(ID id, E entity) throws Exception;

    void delete(ID id) throws Exception;

}
