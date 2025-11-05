package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.CostoVehiculoDTO;
import com.example.mycar.entities.CostoVehiculo;

@Mapper(componentModel = "spring")
public interface CostoVehiculoMapper extends BaseMapper<CostoVehiculo, CostoVehiculoDTO>{

}
