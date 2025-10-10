package com.elva.tp1.service;

import com.elva.tp1.domain.BaseEntity;
import com.elva.tp1.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity, ID> implements CrudHooks<T, ID> {

    protected final BaseRepository<T, ID> baseRepository;

    public BaseService(BaseRepository<T, ID> baseRepository) {
        this.baseRepository = baseRepository;
    }

    public List<T> findAll() {
        return baseRepository.findAll();
    }

    public Optional<T> findById(ID id) {
        beforeRead(id);
        Optional<T> entity = baseRepository.findById(id);
        entity.ifPresent(this::afterRead);
        return entity;
    }

    public T save(T entity) {
        beforeCreate(entity);
        T saved = baseRepository.save(entity);
        afterCreate(saved);
        return saved;
    }

    public void softDeleteById(ID id) {
        beforeDelete(id);
        baseRepository.findById(id).ifPresent(entity -> {
            entity.setEliminado(true);  // soft delete = mark eliminado
            baseRepository.save(entity);
            afterDelete(id);
        });
    }

    public void update(ID id, T newEntity) {
        beforeUpdate(id, newEntity);
        baseRepository.findById(id).map(existing -> {
            newEntity.setId(existing.getId()); // ensure ID stays the same
            T updated = baseRepository.save(newEntity);
            afterUpdate(updated);
            return updated;
        });
    }

    // Default no-op implementations of hooks (override only if needed)
    @Override
    public void beforeCreate(T entity) {
    }

    @Override
    public void afterCreate(T entity) {
    }

    @Override
    public void beforeRead(ID id) {
    }

    @Override
    public void afterRead(T entity) {
    }

    @Override
    public void beforeUpdate(ID id, T entity) {
    }

    @Override
    public void afterUpdate(T entity) {
    }

    @Override
    public void beforeDelete(ID id) {
    }

    @Override
    public void afterDelete(ID id) {
    }
}
