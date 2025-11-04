package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.ImagenDTO;
import com.example.mycar.entities.Imagen;

@Mapper(componentModel = "spring")
public interface ImagenMapper extends BaseMapper<Imagen, ImagenDTO>{

}