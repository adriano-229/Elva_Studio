package com.example.mycar.services.mapper;

import com.example.mycar.dto.BaseDTO;
import com.example.mycar.entities.Base;

import java.util.List;

public interface BaseMapper<E extends Base, D extends BaseDTO> {
    E toEntity(D dto);

    D toDto(E entity);

    List<D> toDtoList(List<E> entities);
}