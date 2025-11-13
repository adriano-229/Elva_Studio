package com.example.mycar.services.mapper;

import com.example.mycar.dto.CaracteristicaVehiculoDTO;
import com.example.mycar.entities.CaracteristicaVehiculo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class CaracteristicaVehiculoMapperImpl implements CaracteristicaVehiculoMapper {

    @Autowired
    private ImagenMapper imagenMapper;
    @Autowired
    private CostoVehiculoMapper costoVehiculoMapper;

    @Override
    public CaracteristicaVehiculo toEntity(CaracteristicaVehiculoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CaracteristicaVehiculo caracteristicaVehiculo = new CaracteristicaVehiculo();

        caracteristicaVehiculo.setId( dto.getId() );
        caracteristicaVehiculo.setActivo( dto.isActivo() );
        caracteristicaVehiculo.setMarca( dto.getMarca() );
        caracteristicaVehiculo.setModelo( dto.getModelo() );
        caracteristicaVehiculo.setCantidadPuerta( dto.getCantidadPuerta() );
        caracteristicaVehiculo.setCantidadAsiento( dto.getCantidadAsiento() );
        caracteristicaVehiculo.setAnio( dto.getAnio() );
        caracteristicaVehiculo.setCantidadTotalVehiculo( dto.getCantidadTotalVehiculo() );
        caracteristicaVehiculo.setCantidadVehiculoAlquilado( dto.getCantidadVehiculoAlquilado() );
        caracteristicaVehiculo.setImagen( imagenMapper.toEntity( dto.getImagen() ) );
        caracteristicaVehiculo.setCostoVehiculo( costoVehiculoMapper.toEntity( dto.getCostoVehiculo() ) );

        return caracteristicaVehiculo;
    }

    @Override
    public CaracteristicaVehiculoDTO toDto(CaracteristicaVehiculo entity) {
        if ( entity == null ) {
            return null;
        }

        CaracteristicaVehiculoDTO.CaracteristicaVehiculoDTOBuilder<?, ?> caracteristicaVehiculoDTO = CaracteristicaVehiculoDTO.builder();

        caracteristicaVehiculoDTO.id( entity.getId() );
        caracteristicaVehiculoDTO.activo( entity.isActivo() );
        caracteristicaVehiculoDTO.marca( entity.getMarca() );
        caracteristicaVehiculoDTO.modelo( entity.getModelo() );
        caracteristicaVehiculoDTO.cantidadPuerta( entity.getCantidadPuerta() );
        caracteristicaVehiculoDTO.cantidadAsiento( entity.getCantidadAsiento() );
        caracteristicaVehiculoDTO.anio( entity.getAnio() );
        caracteristicaVehiculoDTO.cantidadTotalVehiculo( entity.getCantidadTotalVehiculo() );
        caracteristicaVehiculoDTO.cantidadVehiculoAlquilado( entity.getCantidadVehiculoAlquilado() );
        caracteristicaVehiculoDTO.imagen( imagenMapper.toDto( entity.getImagen() ) );
        caracteristicaVehiculoDTO.costoVehiculo( costoVehiculoMapper.toDto( entity.getCostoVehiculo() ) );

        return caracteristicaVehiculoDTO.build();
    }

    @Override
    public List<CaracteristicaVehiculoDTO> toDtoList(List<CaracteristicaVehiculo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CaracteristicaVehiculoDTO> list = new ArrayList<CaracteristicaVehiculoDTO>( entities.size() );
        for ( CaracteristicaVehiculo caracteristicaVehiculo : entities ) {
            list.add( toDto( caracteristicaVehiculo ) );
        }

        return list;
    }
}
