package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.ImagenDTO;
import com.projects.mycar.mycar_server.entities.Imagen;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagenMapper extends BaseMapper<Imagen, ImagenDTO> {

}