package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;

import com.example.contactosApp.domain.Persona;
import com.example.contactosApp.domain.dto.PersonaDTO;

@Mapper(componentModel = "spring")
public interface PersonaMapper extends BaseMapper<Persona, PersonaDTO>{

}
