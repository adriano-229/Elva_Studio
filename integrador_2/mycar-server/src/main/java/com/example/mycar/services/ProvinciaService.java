package com.example.mycar.services;

import com.example.mycar.dto.ProvinciaDTO;

import java.util.List;

public interface ProvinciaService extends BaseService<ProvinciaDTO, Long> {

    List<ProvinciaDTO> findAllActivasOrderByNombreAsc();

    List<ProvinciaDTO> findAllActivasByPaisIdOrderByNombreAsc(Long paisId);

    List<ProvinciaDTO> findAllActivasByPaisNombreOrderByNombreAsc(String paisNombre);


}
