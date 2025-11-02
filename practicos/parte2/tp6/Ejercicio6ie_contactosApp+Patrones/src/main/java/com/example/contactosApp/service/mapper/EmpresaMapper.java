package com.example.contactosApp.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.contactosApp.domain.Empresa;
import com.example.contactosApp.domain.dto.EmpresaDTO;

@Mapper(componentModel = "spring", uses = {ContactoMapper.class})
public interface EmpresaMapper extends BaseMapper<Empresa, EmpresaDTO> {
	
	// ignoramos el mapeo de contacto para evitar el ciclo
    /*@Override
    @Mapping(target = "contacto", ignore = true)
    EmpresaDTO toDto(Empresa entity);

    @Override
    @Mapping(target = "contacto", ignore = true)
    Empresa toEntity(EmpresaDTO dto);*/

}
