package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.DepartamentoDTO;

import java.util.List;

public interface DepartamentoService extends BaseService<DepartamentoDTO, Long> {

    List<DepartamentoDTO> findAllByOrderByNombreAsc();

    List<DepartamentoDTO> findAllActivosByProvinciaIdOrderByNombre(Long provinciaId);

    List<DepartamentoDTO> findAllActivosByProvinciaNombreOrderByNombre(String provinciaNombre);

}
