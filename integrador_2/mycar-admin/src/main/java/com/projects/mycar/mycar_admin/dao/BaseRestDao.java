package com.projects.mycar.mycar_admin.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import com.example.mycar.mycar_admin.domain.BaseDTO;


public interface BaseRestDao<E extends BaseDTO, ID extends Serializable> {

    void crear(E dto) throws Exception;

    void actualizar(E dto) throws Exception;

    void eliminar(ID id) throws Exception;

    boolean existsById(ID id) throws Exception;

    Optional<E> buscarPorId(ID id) throws Exception;

    List<E> listarActivo() throws Exception;

}
