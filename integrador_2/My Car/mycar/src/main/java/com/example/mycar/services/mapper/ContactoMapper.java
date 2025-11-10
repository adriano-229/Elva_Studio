package com.example.mycar.services.mapper;

import com.example.mycar.dto.ContactoDTO;
import com.example.mycar.entities.Contacto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactoMapper extends BaseMapper<Contacto, ContactoDTO> {

}
