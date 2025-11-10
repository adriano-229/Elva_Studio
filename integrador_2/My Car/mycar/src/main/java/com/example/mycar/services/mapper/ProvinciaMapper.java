package com.example.mycar.services.mapper;

import com.example.mycar.dto.ProvinciaDTO;
import com.example.mycar.entities.Provincia;
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
