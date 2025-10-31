package com.elva.videogames.business.logic.services;

import com.elva.videogames.business.domain.entities.BaseEntity;

import java.util.List;

public interface BaseService<E extends BaseEntity> {
    // Template method pattern - defines common CRUD operations
    List<E> findAll();

    E findById(Long id);

    void saveOne(E entity);

    void updateOne(Long id, E entity);

    void deleteById(Long id);

    // Common method for active entities
    List<E> findAllActive();

    E findActiveById(Long id);
}
