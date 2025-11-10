package com.example.mycar.services;

import com.example.mycar.dto.PaisDTO;

import java.util.List;

public interface PaisService extends BaseService<PaisDTO, Long> {
    List<PaisDTO> findAllByOrderByNombreAsc();

    List<PaisDTO> findAllActivosOrderByNombreAsc();

}
