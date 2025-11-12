package com.example.mycar.services.mapper;

import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.entities.Imagen;

import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ImagenMapper extends BaseMapper<Imagen, ImagenDTO> {

}