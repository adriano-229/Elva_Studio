package com.example.biblioteca.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.dao.impl.LocalidadRestDaoImpl;
import com.example.biblioteca.domain.dto.LocalidadDTO;
import com.example.biblioteca.service.LocalidadService;

@Service
public class LocalidadServiceImpl extends BaseServiceImpl<LocalidadDTO, Long> implements LocalidadService{
	
	@Autowired
	private LocalidadRestDaoImpl daoLocalidad;
	
	public LocalidadServiceImpl(BaseRestDao<LocalidadDTO, Long> dao) {
		super(dao);
	}
	
	public void validar(LocalidadDTO localidad) throws Exception{
		
	}
	
	/*public void crear(Long id, String denominacion) throws Exception {
		try {
			
			validar(id, denominacion);
			
			LocalidadDTO localidadDto = new LocalidadDTO();
			localidadDto.setId(id);
			localidadDto.setDenominacion(denominacion);
						
			dao.crear(localidadDto);
			
		} catch (Exception e) {
			throw new Exception("Error al crear localidad", e);
		}
	}

	
	public void actualizar(Long id, String denominacion) throws Exception {
		try {
			
			validar(id, denominacion);
			
			LocalidadDTO localidad = dao.buscarPorId(id);
			localidad.setDenominacion(denominacion);
			
			dao.actualizar(localidad);
			
		} catch (Exception e) {
			throw new Exception("Error al actualizar localidad", e);
		}
	}*/
	
	@Override
	public List<LocalidadDTO> searchByDenominacion(String denominacion) throws Exception {
		try {
			return daoLocalidad.buscarPorDenominacion(denominacion);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	
}
