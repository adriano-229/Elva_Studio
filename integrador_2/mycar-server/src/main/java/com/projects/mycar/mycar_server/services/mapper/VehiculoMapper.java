package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.VehiculoDTO;
import com.projects.mycar.mycar_server.entities.Vehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMapper<Vehiculo, VehiculoDTO> {

}
