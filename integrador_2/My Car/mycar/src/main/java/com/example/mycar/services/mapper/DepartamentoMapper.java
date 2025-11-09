package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.entities.Departamento;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class, LocalidadMapper.class})
public interface DepartamentoMapper extends BaseMapper<Departamento, DepartamentoDTO>{
	
	@Override
    @Mapping(source = "provincia.id", target = "provinciaId")
    DepartamentoDTO toDto(Departamento entity);

    @Override
    @Mapping(source = "provinciaId", target = "provincia.id")
    Departamento toEntity(DepartamentoDTO dto);
}
