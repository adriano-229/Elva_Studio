package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.LocalidadDTO;
import com.projects.mycar.mycar_server.entities.Localidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class})
public interface LocalidadMapper extends BaseMapper<Localidad, LocalidadDTO> {

    @Override
    @Mapping(source = "departamento.id", target = "departamentoId")
    LocalidadDTO toDto(Localidad entity);

    @Override
    @Mapping(source = "departamentoId", target = "departamento.id")
    Localidad toEntity(LocalidadDTO dto);
}
