package com.example.mycar.services.mapper;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ImagenMapper.class, CostoVehiculoMapper.class})
public interface CaracteristicaVehiculoMapper extends BaseMapper<CaracteristicaVehiculo, CaracteristicaVehiculoDTO> {

}
