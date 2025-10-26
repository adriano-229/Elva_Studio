package com.elva.videogames.controllers.rest;

import com.elva.videogames.business.domain.entities.BaseEntity;
import com.elva.videogames.business.logic.services.BaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class BaseRestController<E extends BaseEntity, D> {

    // Template method pattern - abstract methods to be implemented by subclasses
    protected abstract BaseService<E> getService();

    protected abstract D toDTO(E entity);

    protected abstract E toEntity(D dto);

    protected abstract List<D> toDTOList(List<E> entities);

    // Template methods - common REST operations
    @GetMapping
    public ResponseEntity<List<D>> getAll() {
        List<E> entities = getService().findAllActive();
        return ResponseEntity.ok(toDTOList(entities));
    }

    @GetMapping("/{id}")
    public ResponseEntity<D> getById(@PathVariable Long id) {
        E entity = getService().findActiveById(id);
        return ResponseEntity.ok(toDTO(entity));
    }

    @PostMapping
    public ResponseEntity<D> create(@RequestBody D dto) {
        E entity = toEntity(dto);
        getService().saveOne(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDTO(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<D> update(@PathVariable Long id, @RequestBody D dto) {
        E entity = toEntity(dto);
        getService().updateOne(id, entity);
        return ResponseEntity.ok(toDTO(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        getService().deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
