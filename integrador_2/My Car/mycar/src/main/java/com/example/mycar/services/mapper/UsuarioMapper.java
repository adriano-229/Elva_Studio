package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

}
