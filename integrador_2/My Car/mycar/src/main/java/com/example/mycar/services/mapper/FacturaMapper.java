package com.example.mycar.services.mapper;

import com.example.mycar.dto.FacturaDTO;
import com.example.mycar.entities.Factura;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FacturaMapper extends BaseMapper<Factura, FacturaDTO> {

    @Mapping(source = "formaDePago.id", target = "formaDePagoId")
    FacturaDTO toDto(Factura entity);

    @Mapping(source = "formaDePagoId", target = "formaDePago.id")
    Factura toEntity(FacturaDTO dto);
}

