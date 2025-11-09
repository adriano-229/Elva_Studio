package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.PaisDTO;
import com.example.mycar.entities.Pais;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface PaisMapper extends BaseMapper<Pais, PaisDTO>{

}
