package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.LocalidadDTO;
import com.example.mycar.entities.Localidad;

@Mapper(componentModel = "spring", uses = {DepartamentoMapper.class})
public interface LocalidadMapper extends BaseMapper<Localidad, LocalidadDTO>{

}
