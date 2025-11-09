package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.ContactoDTO;
import com.example.mycar.entities.Contacto;

@Mapper(componentModel = "spring")
public interface ContactoMapper extends BaseMapper<Contacto, ContactoDTO>{

}
