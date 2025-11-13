package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.PersonaDTO;
import com.projects.mycar.mycar_server.entities.Persona;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class, ContactoMapper.class, ImagenMapper.class})
public interface PersonaMapper extends BaseMapper<Persona, PersonaDTO> {


}
