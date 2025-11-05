package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMapper<Vehiculo, VehiculoDTO>{

}
