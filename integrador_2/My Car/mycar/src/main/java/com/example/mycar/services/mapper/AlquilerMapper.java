package com.example.mycar.services.mapper;

import com.example.mycar.dto.AlquilerDTO;
import com.example.mycar.entities.Alquiler;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlquilerMapper extends BaseMapper<Alquiler, AlquilerDTO> {
    @Mapping(source = "documentacion.id", target = "documentacionId")
    @Mapping(source = "vehiculo.id", target = "vehiculoId")
    AlquilerDTO toDto(Alquiler entity);

    @Mapping(source = "documentacionId", target = "documentacion.id")
    @Mapping(source = "vehiculoId", target = "vehiculo.id")
    Alquiler toEntity(AlquilerDTO dto);
}
