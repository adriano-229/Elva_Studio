package com.example.mycar.services.mapper;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

}
