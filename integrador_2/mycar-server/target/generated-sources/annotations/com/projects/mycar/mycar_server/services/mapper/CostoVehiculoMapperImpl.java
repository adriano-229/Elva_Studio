package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.CostoVehiculoDTO;
import com.projects.mycar.mycar_server.entities.CostoVehiculo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class CostoVehiculoMapperImpl implements CostoVehiculoMapper {

    @Override
    public CostoVehiculo toEntity(CostoVehiculoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CostoVehiculo costoVehiculo = new CostoVehiculo();

        costoVehiculo.setId( dto.getId() );
        costoVehiculo.setActivo( dto.isActivo() );
        costoVehiculo.setFechaDesde( dto.getFechaDesde() );
        costoVehiculo.setFechaHasta( dto.getFechaHasta() );
        costoVehiculo.setCosto( dto.getCosto() );

        return costoVehiculo;
    }

    @Override
    public CostoVehiculoDTO toDto(CostoVehiculo entity) {
        if ( entity == null ) {
            return null;
        }

        CostoVehiculoDTO.CostoVehiculoDTOBuilder<?, ?> costoVehiculoDTO = CostoVehiculoDTO.builder();

        costoVehiculoDTO.id( entity.getId() );
        costoVehiculoDTO.activo( entity.isActivo() );
        costoVehiculoDTO.fechaDesde( entity.getFechaDesde() );
        costoVehiculoDTO.fechaHasta( entity.getFechaHasta() );
        costoVehiculoDTO.costo( entity.getCosto() );

        return costoVehiculoDTO.build();
    }

    @Override
    public List<CostoVehiculoDTO> toDtoList(List<CostoVehiculo> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CostoVehiculoDTO> list = new ArrayList<CostoVehiculoDTO>( entities.size() );
        for ( CostoVehiculo costoVehiculo : entities ) {
            list.add( toDto( costoVehiculo ) );
        }

        return list;
    }
}
