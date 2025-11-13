package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ClienteDTO;
import com.projects.mycar.mycar_server.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDTO> {

}
