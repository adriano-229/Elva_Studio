package com.example.biblioteca.dao;
import java.util.List;

import com.example.biblioteca.domain.dto.LocalidadDTO;


public interface LocalidadRestDao extends BaseRestDao<LocalidadDTO, Long>{

	List<LocalidadDTO> buscarPorDenominacion(String denominacion) throws Exception;

}
