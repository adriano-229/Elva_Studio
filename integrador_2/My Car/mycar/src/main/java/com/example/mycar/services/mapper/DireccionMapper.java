package com.example.mycar.services.mapper;

import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.entities.Direccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LocalidadMapper.class})
public interface DireccionMapper extends BaseMapper<Direccion, DireccionDTO> {

    @Override
    @Mapping(source = "localidad.id", target = "localidadId")
    DireccionDTO toDto(Direccion entity);

    @Override
    @Mapping(source = "localidadId", target = "localidad.id")
    Direccion toEntity(DireccionDTO dto);

}
