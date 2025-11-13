package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ProvinciaDTO;
import com.projects.mycar.mycar_server.entities.Provincia;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PaisMapper.class, DepartamentoMapper.class})
public interface ProvinciaMapper extends BaseMapper<Provincia, ProvinciaDTO> {

    @Override
    @Mapping(source = "pais.id", target = "paisId")
    ProvinciaDTO toDto(Provincia entity);

    @Override
    @Mapping(source = "paisId", target = "pais.id")
    Provincia toEntity(ProvinciaDTO dto);

}
