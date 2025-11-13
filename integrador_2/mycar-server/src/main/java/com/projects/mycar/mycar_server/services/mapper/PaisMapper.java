package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.PaisDTO;
import com.projects.mycar.mycar_server.entities.Pais;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface PaisMapper extends BaseMapper<Pais, PaisDTO> {

}
