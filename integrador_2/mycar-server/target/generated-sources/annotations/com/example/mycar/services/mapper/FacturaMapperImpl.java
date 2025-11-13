package com.example.mycar.services.mapper;

import com.example.mycar.dto.DetalleFacturaDTO;
import com.example.mycar.dto.FacturaDTO;
import com.example.mycar.entities.CodigoDescuento;
import com.example.mycar.entities.DetalleFactura;
import com.example.mycar.entities.Factura;
import com.example.mycar.entities.FormaDePago;
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
public class FacturaMapperImpl implements FacturaMapper {

    @Override
    public List<FacturaDTO> toDtoList(List<Factura> entities) {
        if ( entities == null ) {
            return null;
        }

        List<FacturaDTO> list = new ArrayList<FacturaDTO>( entities.size() );
        for ( Factura factura : entities ) {
            list.add( toDto( factura ) );
        }

        return list;
    }

    @Override
    public FacturaDTO toDto(Factura entity) {
        if ( entity == null ) {
            return null;
        }

        FacturaDTO.FacturaDTOBuilder<?, ?> facturaDTO = FacturaDTO.builder();

        facturaDTO.formaDePagoId( entityFormaDePagoId( entity ) );
        facturaDTO.codigoDescuentoCodigo( entityCodigoDescuentoCodigo( entity ) );
        facturaDTO.id( entity.getId() );
        facturaDTO.activo( entity.isActivo() );
        facturaDTO.numeroFactura( entity.getNumeroFactura() );
        facturaDTO.fechaFactura( entity.getFechaFactura() );
        facturaDTO.subtotal( entity.getSubtotal() );
        facturaDTO.descuento( entity.getDescuento() );
        facturaDTO.porcentajeDescuento( entity.getPorcentajeDescuento() );
        facturaDTO.totalPagado( entity.getTotalPagado() );
        facturaDTO.estado( entity.getEstado() );
        facturaDTO.observacionPago( entity.getObservacionPago() );
        facturaDTO.observacionAnulacion( entity.getObservacionAnulacion() );
        facturaDTO.detalles( detalleFacturaListToDetalleFacturaDTOList( entity.getDetalles() ) );

        return facturaDTO.build();
    }

    @Override
    public Factura toEntity(FacturaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Factura.FacturaBuilder factura = Factura.builder();

        if ( dto.getNumeroFactura() != null ) {
            factura.numeroFactura( dto.getNumeroFactura() );
        }
        factura.fechaFactura( dto.getFechaFactura() );
        factura.subtotal( dto.getSubtotal() );
        factura.descuento( dto.getDescuento() );
        factura.porcentajeDescuento( dto.getPorcentajeDescuento() );
        factura.totalPagado( dto.getTotalPagado() );
        factura.estado( dto.getEstado() );
        factura.observacionPago( dto.getObservacionPago() );
        factura.observacionAnulacion( dto.getObservacionAnulacion() );

        return factura.build();
    }

    private Long entityFormaDePagoId(Factura factura) {
        FormaDePago formaDePago = factura.getFormaDePago();
        if ( formaDePago == null ) {
            return null;
        }
        return formaDePago.getId();
    }

    private String entityCodigoDescuentoCodigo(Factura factura) {
        CodigoDescuento codigoDescuento = factura.getCodigoDescuento();
        if ( codigoDescuento == null ) {
            return null;
        }
        return codigoDescuento.getCodigo();
    }

    protected DetalleFacturaDTO detalleFacturaToDetalleFacturaDTO(DetalleFactura detalleFactura) {
        if ( detalleFactura == null ) {
            return null;
        }

        DetalleFacturaDTO.DetalleFacturaDTOBuilder<?, ?> detalleFacturaDTO = DetalleFacturaDTO.builder();

        detalleFacturaDTO.id( detalleFactura.getId() );
        detalleFacturaDTO.activo( detalleFactura.isActivo() );
        detalleFacturaDTO.cantidad( detalleFactura.getCantidad() );
        detalleFacturaDTO.subtotal( detalleFactura.getSubtotal() );

        return detalleFacturaDTO.build();
    }

    protected List<DetalleFacturaDTO> detalleFacturaListToDetalleFacturaDTOList(List<DetalleFactura> list) {
        if ( list == null ) {
            return null;
        }

        List<DetalleFacturaDTO> list1 = new ArrayList<DetalleFacturaDTO>( list.size() );
        for ( DetalleFactura detalleFactura : list ) {
            list1.add( detalleFacturaToDetalleFacturaDTO( detalleFactura ) );
        }

        return list1;
    }
}
