package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.UsuarioDTO;
import com.projects.mycar.mycar_server.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PersonaMapper.class})
public interface UsuarioMapper extends BaseMapper<Usuario, UsuarioDTO> {

}
