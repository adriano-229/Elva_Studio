package com.example.mycar.services.mapper;

import com.example.mycar.dto.PaisDTO;
import com.example.mycar.entities.Pais;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface PaisMapper extends BaseMapper<Pais, PaisDTO> {

}
