package com.projects.mycar.mycar_server.services.mapper;

import com.projects.mycar.mycar_server.dto.AlquilerDTO;
import com.projects.mycar.mycar_server.entities.Alquiler;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DocumentacionMapper.class, VehiculoMapper.class, ClienteMapper.class})
public interface AlquilerMapper extends BaseMapper<Alquiler, AlquilerDTO> {
    //@Mapping(source = "documentacion.id", target = "documentacionId")
    //@Mapping(source = "vehiculo.id", target = "vehiculoId")
    //@Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(target = "clienteId", ignore = true)
    @Mapping(target = "vehiculoId", ignore = true)
    AlquilerDTO toDto(Alquiler entity);

    //@Mapping(source = "documentacionId", target = "documentacion.id")
    //@Mapping(source = "vehiculoId", target = "vehiculo.id")
    @Mapping(target = "detalleFactura", ignore = true)
    @Mapping(target = "version", ignore = true)
    //@Mapping(source = "vehiculoId", target = "vehiculo.id")
    //@Mapping(source = "vehiculoId", target = "cliente.id")
    Alquiler toEntity(AlquilerDTO dto);
}
