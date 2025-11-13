package com.example.mycar.services.mapper;

import com.example.mycar.dto.DetalleFacturaDTO;
import com.example.mycar.entities.Alquiler;
import com.example.mycar.entities.DetalleFactura;
import com.example.mycar.entities.Factura;
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
public class DetalleFacturaMapperImpl implements DetalleFacturaMapper {

    @Override
    public List<DetalleFacturaDTO> toDtoList(List<DetalleFactura> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DetalleFacturaDTO> list = new ArrayList<DetalleFacturaDTO>( entities.size() );
        for ( DetalleFactura detalleFactura : entities ) {
            list.add( toDto( detalleFactura ) );
        }

        return list;
    }

    @Override
    public DetalleFacturaDTO toDto(DetalleFactura entity) {
        if ( entity == null ) {
            return null;
        }

        DetalleFacturaDTO.DetalleFacturaDTOBuilder<?, ?> detalleFacturaDTO = DetalleFacturaDTO.builder();

        detalleFacturaDTO.facturaId( entityFacturaId( entity ) );
        detalleFacturaDTO.alquilerId( entityAlquilerId( entity ) );
        detalleFacturaDTO.id( entity.getId() );
        detalleFacturaDTO.activo( entity.isActivo() );
        detalleFacturaDTO.cantidad( entity.getCantidad() );
        detalleFacturaDTO.subtotal( entity.getSubtotal() );

        return detalleFacturaDTO.build();
    }

    @Override
    public DetalleFactura toEntity(DetalleFacturaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        DetalleFactura detalleFactura = new DetalleFactura();

        detalleFactura.setId( dto.getId() );
        detalleFactura.setActivo( dto.isActivo() );
        if ( dto.getCantidad() != null ) {
            detalleFactura.setCantidad( dto.getCantidad() );
        }
        if ( dto.getSubtotal() != null ) {
            detalleFactura.setSubtotal( dto.getSubtotal() );
        }

        return detalleFactura;
    }

    private Long entityFacturaId(DetalleFactura detalleFactura) {
        Factura factura = detalleFactura.getFactura();
        if ( factura == null ) {
            return null;
        }
        return factura.getId();
    }

    private Long entityAlquilerId(DetalleFactura detalleFactura) {
        Alquiler alquiler = detalleFactura.getAlquiler();
        if ( alquiler == null ) {
            return null;
        }
        return alquiler.getId();
    }
}
