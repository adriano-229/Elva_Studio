package com.example.mycar.services.mapper;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CaracteristicaVehiculoMapper extends BaseMapper<CaracteristicaVehiculo, CaracteristicaVehiculoDTO> {

}
