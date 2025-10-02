package com.elva.tp1.service;

import com.elva.tp1.entity.Activable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.Collectors;

public class GenericServiceImpl<T extends Activable, ID> implements GenericService<T, ID> {

    private final JpaRepository<T, ID> repository;

    public GenericServiceImpl(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public void save(T entity) {
        repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.findById(id).ifPresent(e -> {
            e.desactivar();
            repository.save(e);
        });
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public List<T> findAllActive() {
        return repository.findAll().stream()
                .filter(Activable::isActivo)
                .collect(Collectors.toList());
    }
}
