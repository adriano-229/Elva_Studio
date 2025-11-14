package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DireccionDTO;
import com.projects.mycar.mycar_server.entities.Direccion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {LocalidadSimpleMapper.class})
public interface DireccionMapper extends BaseMapper<Direccion, DireccionDTO> {

    @Override
    @Mapping(source = "localidad.id", target = "localidadId")
    DireccionDTO toDto(Direccion entity);

    @Override
    @Mapping(source = "localidadId", target = "localidad.id")
    Direccion toEntity(DireccionDTO dto);

}
