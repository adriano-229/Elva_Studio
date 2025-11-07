package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.ProvinciaDTO;
import com.example.mycar.entities.Provincia;

@Mapper(componentModel = "spring", uses = {PaisMapper.class})
public interface ProvinciaMapper extends BaseMapper<Provincia, ProvinciaDTO>{

}
