package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.DetalleFacturaDTO;
import com.projects.mycar.mycar_server.entities.DetalleFactura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleFacturaMapper extends BaseMapper<DetalleFactura, DetalleFacturaDTO> {

    @Mapping(source = "factura.id", target = "facturaId")
    @Mapping(source = "alquiler.id", target = "alquilerId")
    DetalleFacturaDTO toDto(DetalleFactura entity);

    @Mapping(target = "factura", ignore = true)
    @Mapping(target = "alquiler", ignore = true)
    DetalleFactura toEntity(DetalleFacturaDTO dto);
}

