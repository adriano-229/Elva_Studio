package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.AlquilerDTO;
import com.projects.mycar.mycar_server.entities.Alquiler;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T21:15:46-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 17.0.17 (Microsoft)"
)
@Component
public class AlquilerMapperImpl implements AlquilerMapper {

    @Autowired
    private DocumentacionMapper documentacionMapper;
    @Autowired
    private VehiculoMapper vehiculoMapper;
    @Autowired
    private ClienteMapper clienteMapper;

    @Override
    public List<AlquilerDTO> toDtoList(List<Alquiler> entities) {
        if ( entities == null ) {
            return null;
        }

        List<AlquilerDTO> list = new ArrayList<AlquilerDTO>( entities.size() );
        for ( Alquiler alquiler : entities ) {
            list.add( toDto( alquiler ) );
        }

        return list;
    }

    @Override
    public AlquilerDTO toDto(Alquiler entity) {
        if ( entity == null ) {
            return null;
        }

        AlquilerDTO.AlquilerDTOBuilder<?, ?> alquilerDTO = AlquilerDTO.builder();

        alquilerDTO.id( entity.getId() );
        alquilerDTO.activo( entity.isActivo() );
        alquilerDTO.fechaDesde( entity.getFechaDesde() );
        alquilerDTO.fechaHasta( entity.getFechaHasta() );
        alquilerDTO.documentacion( documentacionMapper.toDto( entity.getDocumentacion() ) );
        alquilerDTO.vehiculo( vehiculoMapper.toDto( entity.getVehiculo() ) );
        alquilerDTO.cliente( clienteMapper.toDto( entity.getCliente() ) );
        alquilerDTO.costoCalculado( entity.getCostoCalculado() );
        alquilerDTO.cantidadDias( entity.getCantidadDias() );

        return alquilerDTO.build();
    }

    @Override
    public Alquiler toEntity(AlquilerDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Alquiler alquiler = new Alquiler();

        alquiler.setId( dto.getId() );
        alquiler.setActivo( dto.isActivo() );
        alquiler.setFechaDesde( dto.getFechaDesde() );
        alquiler.setFechaHasta( dto.getFechaHasta() );
        alquiler.setCostoCalculado( dto.getCostoCalculado() );
        alquiler.setCantidadDias( dto.getCantidadDias() );
        alquiler.setDocumentacion( documentacionMapper.toEntity( dto.getDocumentacion() ) );
        alquiler.setVehiculo( vehiculoMapper.toEntity( dto.getVehiculo() ) );
        alquiler.setCliente( clienteMapper.toEntity( dto.getCliente() ) );

        return alquiler;
    }
}
