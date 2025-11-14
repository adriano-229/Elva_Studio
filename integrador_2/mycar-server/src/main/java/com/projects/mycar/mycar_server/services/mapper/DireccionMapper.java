package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DireccionDTO;
import com.projects.mycar.mycar_server.entities.Direccion;
import com.projects.mycar.mycar_server.entities.Localidad;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {LocalidadSimpleMapper.class})
public interface DireccionMapper extends BaseMapper<Direccion, DireccionDTO> {

    @Override
    @Mapping(source = "localidad.id", target = "localidadId")
    @Mapping(source = "localidad", target = "localidad", qualifiedByName = "localidadSinDireccionesDto")
    DireccionDTO toDto(Direccion entity);

    @Override
    @Mapping(source = "localidad", target = "localidad", qualifiedByName = "localidadSinDireccionesEntity")
    Direccion toEntity(DireccionDTO dto);

    @AfterMapping
    default void fillLocalidadId(DireccionDTO dto, @MappingTarget Direccion entity) {
        if (dto == null) {
            return;
        }
        Long localidadId = dto.getLocalidadId();
        if (localidadId == null) {
            return;
        }
        Localidad localidad = entity.getLocalidad();
        if (localidad == null) {
            localidad = new Localidad();
            entity.setLocalidad(localidad);
        }
        localidad.setId(localidadId);
    }
}
