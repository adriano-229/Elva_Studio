package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ContactoDTO;
import com.projects.mycar.mycar_server.entities.Contacto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContactoMapper extends BaseMapper<Contacto, ContactoDTO> {

}
