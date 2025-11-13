package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.FacturaDTO;
import com.projects.mycar.mycar_server.entities.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper extends BaseMapper<Factura, FacturaDTO> {

    @Mapping(source = "formaDePago.id", target = "formaDePagoId")
    @Mapping(source = "codigoDescuento.codigo", target = "codigoDescuentoCodigo")
    @Mapping(target = "formaDePagoTexto", ignore = true)
    FacturaDTO toDto(Factura entity);

    @Mapping(target = "formaDePago", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    @Mapping(target = "codigoDescuento", ignore = true)
    Factura toEntity(FacturaDTO dto);
}
