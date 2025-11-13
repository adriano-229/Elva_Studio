package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DocumentacionDTO;
import com.projects.mycar.mycar_server.entities.Documentacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentacionMapper extends BaseMapper<Documentacion, DocumentacionDTO> {

}
