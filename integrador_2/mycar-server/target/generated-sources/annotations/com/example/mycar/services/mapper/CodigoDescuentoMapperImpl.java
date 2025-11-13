package com.example.mycar.services.mapper;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.entities.Cliente;
import com.example.mycar.entities.CodigoDescuento;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T13:03:13-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.8 (BellSoft)"
)
@Component
public class CodigoDescuentoMapperImpl implements CodigoDescuentoMapper {

    @Override
    public CodigoDescuento toEntity(CodigoDescuentoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CodigoDescuento.CodigoDescuentoBuilder codigoDescuento = CodigoDescuento.builder();

        codigoDescuento.codigo( dto.getCodigo() );
        codigoDescuento.porcentajeDescuento( dto.getPorcentajeDescuento() );
        codigoDescuento.fechaGeneracion( dto.getFechaGeneracion() );
        codigoDescuento.fechaExpiracion( dto.getFechaExpiracion() );
        codigoDescuento.utilizado( dto.getUtilizado() );
        codigoDescuento.fechaUtilizacion( dto.getFechaUtilizacion() );

        return codigoDescuento.build();
    }

    @Override
    public List<CodigoDescuentoDTO> toDtoList(List<CodigoDescuento> entities) {
        if ( entities == null ) {
            return null;
        }

        List<CodigoDescuentoDTO> list = new ArrayList<CodigoDescuentoDTO>( entities.size() );
        for ( CodigoDescuento codigoDescuento : entities ) {
            list.add( toDto( codigoDescuento ) );
        }

        return list;
    }

    @Override
    public CodigoDescuentoDTO toDto(CodigoDescuento entity) {
        if ( entity == null ) {
            return null;
        }

        CodigoDescuentoDTO codigoDescuentoDTO = new CodigoDescuentoDTO();

        codigoDescuentoDTO.setClienteId( entityClienteId( entity ) );
        codigoDescuentoDTO.setClienteNombre( entityClienteNombre( entity ) );
        codigoDescuentoDTO.setActivo( entity.isActivo() );
        codigoDescuentoDTO.setId( entity.getId() );
        codigoDescuentoDTO.setCodigo( entity.getCodigo() );
        codigoDescuentoDTO.setPorcentajeDescuento( entity.getPorcentajeDescuento() );
        codigoDescuentoDTO.setFechaGeneracion( entity.getFechaGeneracion() );
        codigoDescuentoDTO.setFechaExpiracion( entity.getFechaExpiracion() );
        codigoDescuentoDTO.setUtilizado( entity.getUtilizado() );
        codigoDescuentoDTO.setFechaUtilizacion( entity.getFechaUtilizacion() );

        return codigoDescuentoDTO;
    }

    private Long entityClienteId(CodigoDescuento codigoDescuento) {
        Cliente cliente = codigoDescuento.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getId();
    }

    private String entityClienteNombre(CodigoDescuento codigoDescuento) {
        Cliente cliente = codigoDescuento.getCliente();
        if ( cliente == null ) {
            return null;
        }
        return cliente.getNombre();
    }
}
