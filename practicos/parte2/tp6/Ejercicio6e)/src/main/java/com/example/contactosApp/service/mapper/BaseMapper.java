package com.example.contactosApp.service.mapper;

import java.util.List;
import com.example.contactosApp.domain.Base;
import com.example.contactosApp.domain.dto.BaseDTO;

public interface BaseMapper<E extends Base, D extends BaseDTO> {
    E toEntity(D dto);
    D toDto(E entity);
    List<D> toDtoList(List<E> entities);
}
