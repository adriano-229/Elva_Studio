package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.mycar.dto.CodigoDescuentoDTO;
import com.example.mycar.entities.CodigoDescuento;

@Mapper(componentModel = "spring", uses = {ClienteMapper.class})
public interface CodigoDescuentoMapper extends BaseMapper<CodigoDescuento, CodigoDescuentoDTO>{
	
	@Mapping(source = "cliente.id", target = "clienteId")
	@Mapping(source = "cliente.nombre", target = "clienteNombre")
	CodigoDescuentoDTO toDto(CodigoDescuento entity);
	
	


	

}
