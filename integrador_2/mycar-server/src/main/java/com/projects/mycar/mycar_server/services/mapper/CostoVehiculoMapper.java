package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.CostoVehiculoDTO;
import com.projects.mycar.mycar_server.entities.CostoVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostoVehiculoMapper extends BaseMapper<CostoVehiculo, CostoVehiculoDTO> {

}
