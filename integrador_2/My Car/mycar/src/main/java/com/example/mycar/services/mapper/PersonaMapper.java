package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.PersonaDTO;
import com.example.mycar.entities.Persona;

@Mapper(componentModel = "spring", uses = {DireccionMapper.class})
public interface PersonaMapper extends BaseMapper<Persona, PersonaDTO>{
	

}
