package com.example.mycar.services;

import java.util.List;

import com.example.mycar.dto.DireccionDTO;

public interface DireccionService extends BaseService<DireccionDTO, Long>{
	List<DireccionDTO> findAllByOrderByCalleAsc();
	List<DireccionDTO> findAllByDepartamento_NombreOrderByCalleAsc(String departamentoNombre);
	String getDireccionCompleta(DireccionDTO direccion) throws Exception;
	String getGoogleMapsUrl(DireccionDTO direccion) throws Exception;
}
