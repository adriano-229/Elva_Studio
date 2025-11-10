package com.example.mycar.services;

import com.example.mycar.dto.DepartamentoDTO;

import java.util.List;

public interface DepartamentoService extends BaseService<DepartamentoDTO, Long> {

    List<DepartamentoDTO> findAllByOrderByNombreAsc();

    List<DepartamentoDTO> findAllActivosByProvinciaIdOrderByNombre(Long provinciaId);

    List<DepartamentoDTO> findAllActivosByProvinciaNombreOrderByNombre(String provinciaNombre);

}
