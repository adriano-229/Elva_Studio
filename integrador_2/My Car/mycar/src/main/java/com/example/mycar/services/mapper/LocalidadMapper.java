package com.example.mycar.services.mapper;

import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Localidad;
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
