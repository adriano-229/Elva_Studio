package com.example.mycar.services.mapper;

import com.example.mycar.dto.ClienteDTO;
import com.example.mycar.entities.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDTO> {

}
