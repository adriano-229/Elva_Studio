package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ClienteDTO;
import com.projects.mycar.mycar_server.entities.Cliente;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class, LocalidadSimpleMapper.class})
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDTO> {

    @Override
    @Mapping(source = "direccion.localidad", target = "direccion.localidad", qualifiedByName = "localidadSinDireccionesDto")
    ClienteDTO toDto(Cliente entity);

    @Override
    @Mapping(source = "direccion.localidad", target = "direccion.localidad", qualifiedByName = "localidadSinDireccionesEntity")
    Cliente toEntity(ClienteDTO dto);
}
