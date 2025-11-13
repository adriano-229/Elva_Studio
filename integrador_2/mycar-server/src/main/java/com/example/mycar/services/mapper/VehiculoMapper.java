package com.example.mycar.services.mapper;

import com.example.mycar.dto.VehiculoDTO;
import com.example.mycar.entities.Vehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMapper<Vehiculo, VehiculoDTO> {

}
