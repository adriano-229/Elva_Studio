package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.PaisDTO;
import com.example.mycar.entities.Pais;

@Mapper(componentModel = "spring")
public interface PaisMapper extends BaseMapper<Pais, PaisDTO>{

}
