package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.DocumentacionDTO;
import com.example.mycar.entities.Documentacion;

@Mapper(componentModel="spring")
public interface DocumentacionMapper extends BaseMapper<Documentacion, DocumentacionDTO>{

}
