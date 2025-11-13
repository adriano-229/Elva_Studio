package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.BaseDTO;

import java.io.Serializable;
import java.util.List;

public interface BaseService<D extends BaseDTO, ID extends Serializable> {

    List<D> findAll() throws Exception;

    List<D> findAllByIds(Iterable<ID> ids) throws Exception;

    //public Page<E> findAll(Pageable pageable) throws Exception;

    D findById(ID id) throws Exception;

    D save(D entityDTO) throws Exception;

    D update(ID id, D entityDTO) throws Exception;

    boolean delete(ID id) throws Exception;

}
