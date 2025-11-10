package com.example.mycar.services.mapper;

import com.example.mycar.dto.CostoVehiculoDTO;
import com.example.mycar.entities.CostoVehiculo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostoVehiculoMapper extends BaseMapper<CostoVehiculo, CostoVehiculoDTO> {

}
