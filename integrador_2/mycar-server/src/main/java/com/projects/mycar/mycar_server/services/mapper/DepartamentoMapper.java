package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DepartamentoDTO;
import com.projects.mycar.mycar_server.entities.Departamento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class, LocalidadMapper.class})
public interface DepartamentoMapper extends BaseMapper<Departamento, DepartamentoDTO> {

    @Override
    @Mapping(source = "provincia.id", target = "provinciaId")
    DepartamentoDTO toDto(Departamento entity);

    @Override
    @Mapping(source = "provinciaId", target = "provincia.id")
    Departamento toEntity(DepartamentoDTO dto);
}
