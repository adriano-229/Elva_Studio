package com.example.mycar.services.mapper;

import org.mapstruct.Mapper;

import com.example.mycar.dto.DepartamentoDTO;
import com.example.mycar.entities.Departamento;

@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface DepartamentoMapper extends BaseMapper<Departamento, DepartamentoDTO>{

}
