package com.example.mycar.services.mapper;

import com.example.mycar.dto.DocumentacionDTO;
import com.example.mycar.entities.Documentacion;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentacionMapper extends BaseMapper<Documentacion, DocumentacionDTO> {

}
