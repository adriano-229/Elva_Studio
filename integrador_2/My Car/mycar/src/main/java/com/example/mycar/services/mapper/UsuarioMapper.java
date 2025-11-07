package com.example.mycar.services.mapper;

import com.example.mycar.dto.UsuarioDTO;
import com.example.mycar.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

}
