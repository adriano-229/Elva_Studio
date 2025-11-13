package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.ProvinciaDTO;

import java.util.List;

public interface ProvinciaService extends BaseService<ProvinciaDTO, Long> {

    List<ProvinciaDTO> findAllActivasOrderByNombreAsc();

    List<ProvinciaDTO> findAllActivasByPaisIdOrderByNombreAsc(Long paisId);

    List<ProvinciaDTO> findAllActivasByPaisNombreOrderByNombreAsc(String paisNombre);


}
