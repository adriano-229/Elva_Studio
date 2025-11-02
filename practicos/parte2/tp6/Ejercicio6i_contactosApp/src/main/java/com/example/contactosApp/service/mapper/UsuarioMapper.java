package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.contactosApp.domain.Usuario;
import com.example.contactosApp.domain.dto.UsuarioDTO;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO>{

}
