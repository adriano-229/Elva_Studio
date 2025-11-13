package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.BaseDTO;
import com.projects.mycar.mycar_server.entities.Base;

import java.util.List;

public interface BaseMapper<E extends Base, D extends BaseDTO> {
    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDtoList(List<E> entities);
}