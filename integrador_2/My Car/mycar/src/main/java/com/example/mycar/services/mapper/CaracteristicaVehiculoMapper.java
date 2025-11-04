package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;

@Mapper(componentModel = "spring")
public interface CaracteristicaVehiculoMapper extends BaseMapper<CaracteristicaVehiculo, CaracteristicaVehiculoDTO>{

}
