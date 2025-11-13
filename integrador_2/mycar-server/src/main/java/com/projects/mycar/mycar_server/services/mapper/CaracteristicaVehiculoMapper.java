package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.CaracteristicaVehiculoDTO;
import com.projects.mycar.mycar_server.entities.CaracteristicaVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImagenMapper.class, CostoVehiculoMapper.class})
public interface CaracteristicaVehiculoMapper extends BaseMapper<CaracteristicaVehiculo, CaracteristicaVehiculoDTO> {

}
