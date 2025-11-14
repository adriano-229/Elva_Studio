package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.LocalidadDTO;
import com.projects.mycar.mycar_server.entities.Localidad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper to embed locality data inside other DTOs without dragging DireccionMapper
 * to avoid circular Spring bean dependencies.
 */
@Mapper(componentModel = "spring")
public interface LocalidadSimpleMapper {

    @Named("localidadSinDireccionesDto")
    @Mapping(source = "departamento.id", target = "departamentoId")
    @Mapping(target = "direcciones", ignore = true)
    LocalidadDTO toDto(Localidad entity);

    @Named("localidadSinDireccionesEntity")
    @Mapping(source = "departamentoId", target = "departamento.id")
    @Mapping(target = "direcciones", ignore = true)
    Localidad toEntity(LocalidadDTO dto);
}
