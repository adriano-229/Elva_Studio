package com.example.mycar.services.mapper;

import com.example.mycar.dto.PersonaDTO;
import com.example.mycar.entities.Persona;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class, ContactoMapper.class, ImagenMapper.class})
public interface PersonaMapper extends BaseMapper<Persona, PersonaDTO> {


}
