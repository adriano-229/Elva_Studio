package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.DireccionDTO;
import com.example.mycar.entities.Direccion;

@Mapper(componentModel = "spring", uses = {LocalidadMapper.class})
public interface DireccionMapper extends BaseMapper<Direccion, DireccionDTO>{

}
