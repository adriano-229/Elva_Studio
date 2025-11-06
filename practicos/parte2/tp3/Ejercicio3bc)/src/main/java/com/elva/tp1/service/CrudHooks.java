package com.elva.tp1.service;

public interface CrudHooks<T, ID> {

    void beforeCreate(T entity);

    void afterCreate(T entity);

    void beforeRead(ID id);

    void afterRead(T entity);

    void beforeUpdate(ID id, T entity);

    void afterUpdate(T entity);

    void beforeDelete(ID id);

    void afterDelete(ID id);
}
