package com.elva.tp1.service;

import com.elva.tp1.entity.Activable;

import java.util.List;

public interface GenericService<T extends Activable, ID> {
    void save(T entity);

    void delete(ID id); // borrado lógico

    T findById(ID id);

    List<T> findAll();

    List<T> findAllActive();
}
