package com.example.biblioteca.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.example.biblioteca.dao.LibroRestDao;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.domain.enums.TipoBusqueda;

// ANTES DE HACER ESTO HAY QUE COMPLETAR EL CONTROLADOR EN LA APP DE PRODUCTOR
@Repository
public class LibroRestDaoImpl extends BaseRestDaoImpl<LibroDTO, Long> implements LibroRestDao{

	public LibroRestDaoImpl() {
		super(LibroDTO.class, LibroDTO[].class, "http://localhost:9000/api/v1/libros");
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<LibroDTO> buscarPorAutor(String filtro) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LibroDTO> buscarPorGenero(String genero) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LibroDTO buscarPorTitulo(String titulo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<LibroDTO> buscarPorFiltro(String filtro) throws Exception {
		
		try {
			
			String uri = baseUrl + "/search?filtro=" + filtro;
			ResponseEntity<LibroDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
			LibroDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<LibroDTO>();
			}
			
			return Arrays.asList(array);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar libro", e);
		}
	}
	
	
	public List<LibroDTO> buscarPorFiltro(TipoBusqueda busqueda, String filtro) throws Exception {
		
		try {
			
			String uri = baseUrl + "/search?tipo=" + busqueda.name() + "&filtro=" + filtro;
			ResponseEntity<LibroDTO[]> response = restTemplate.getForEntity(uri, entityArrayClass);
			LibroDTO[] array = response.getBody();
			
			if (array == null) {
				return new ArrayList<LibroDTO>();
			}
			
			return Arrays.asList(array);
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al buscar libro", e);
		}
	}

}
