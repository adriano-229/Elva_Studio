package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.CodigoDescuentoDTO;
import com.projects.mycar.mycar_server.entities.CodigoDescuento;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CodigoDescuentoMapper extends BaseMapper<CodigoDescuento, CodigoDescuentoDTO> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    CodigoDescuentoDTO toDto(CodigoDescuento entity);


}
