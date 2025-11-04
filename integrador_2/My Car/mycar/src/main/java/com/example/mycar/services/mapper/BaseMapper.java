package com.example.mycar.services.mapper;

import java.util.List;

import com.example.mycar.dto.BaseDTO;
import com.example.mycar.entities.Base;

public interface BaseMapper<E extends Base, D extends BaseDTO> {
    E toEntity(D dto);
    D toDto(E entity);
    List<D> toDtoList(List<E> entities);
}