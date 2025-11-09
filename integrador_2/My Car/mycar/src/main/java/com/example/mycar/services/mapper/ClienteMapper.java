package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.ClienteDTO;
import com.example.mycar.entities.Cliente;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface ClienteMapper extends BaseMapper<Cliente, ClienteDTO>{

}
