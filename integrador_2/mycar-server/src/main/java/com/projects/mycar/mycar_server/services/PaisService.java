package com.projects.mycar.mycar_server.services;

import com.projects.mycar.mycar_server.dto.PaisDTO;

import java.util.List;

public interface PaisService extends BaseService<PaisDTO, Long> {
    List<PaisDTO> findAllByOrderByNombreAsc();

    List<PaisDTO> findAllActivosOrderByNombreAsc();

}
