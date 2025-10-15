package com.example.biblioteca.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.dao.impl.LibroRestDaoImpl;
import com.example.biblioteca.domain.dto.AutorDTO;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.service.LibroService;

// ANTES DE HACER ESTO HAY QUE COMPLETAR -> LIBRO REST DAO IMPL
@Service
public class LibroServiceImpl extends BaseServiceImpl<LibroDTO, Long> implements LibroService{
	
	@Autowired
	private LibroRestDaoImpl daoLibro;
	
	@Autowired
	private AutorServiceImpl svcAutor;
	
	public LibroServiceImpl(BaseRestDao<LibroDTO, Long> dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}
	
	public void validar(LibroDTO libro) throws Exception {
		
		/*MultipartFile pdf = libro.getPdf();
		// Hacemos validaciones sobre el pdf del lado del consumidor
    	
    	if (pdf.isEmpty()) {
    		throw new Exception("No ha seleccionado un pdf");
	    }

	    // Validar extensión
    	String nombreArchivo = pdf.getOriginalFilename();
    	if (nombreArchivo == null || !nombreArchivo.toLowerCase().endsWith(".pdf")) {
    	    throw new Exception("Solo se permiten archivos PDF");
    	}*/
    }
	
	
	@Override
	public void save(LibroDTO libro) throws Exception {
		try {
			validar(libro);
			
			List<AutorDTO> lista = new ArrayList<>();
			
			for (Long id: libro.getAutoresIds()) {
				lista.add(svcAutor.findById(id));
			}
			
			libro.setAutores(lista);
			
			dao.crear(libro);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public void update(Long id, LibroDTO libro) throws Exception {
		try {
			System.out.println("UPDATE DE LIBRO SERVICE");
			validar(libro);
			dao.buscarPorId(id)
				.orElseThrow(() -> new RuntimeException("Entidad no encontrada"));
			
			libro.setId((Long) id);
			
			List<AutorDTO> lista = new ArrayList<>();
			
			for (Long idAutor: libro.getAutoresIds()) {
				lista.add(svcAutor.findById(idAutor));
			}
			
			libro.setAutores(lista);
			
			dao.actualizar(libro);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
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
	public List<LibroDTO> buscarPorTitulo(String titulo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public LibroDTO searchByTitulo(String titulo) throws Exception {
		try {
			return daoLibro.buscarPorTitulo(titulo);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<LibroDTO> buscarPorFiltro(String filtro) throws Exception {
		
		try {
			return daoLibro.buscarPorFiltro(filtro);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
