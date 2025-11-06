package com.example.biblioteca.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.dao.BaseRestDao;
import com.example.biblioteca.dao.impl.PersonaRestDaoImpl;
import com.example.biblioteca.domain.dto.LibroDTO;
import com.example.biblioteca.domain.dto.PersonaDTO;
import com.example.biblioteca.domain.enums.EstadoLibro;
import com.example.biblioteca.service.PersonaService;

@Service
public class PersonaServiceImpl extends BaseServiceImpl<PersonaDTO, Long> implements PersonaService{
	
	@Autowired
	private PersonaRestDaoImpl daoPersona;
	
	@Autowired
	private LibroServiceImpl svcLibro;
	
	public PersonaServiceImpl(BaseRestDao<PersonaDTO, Long> dao) {
		super(dao);
	}
	
	@Override
	public void validar(PersonaDTO persona) throws Exception{
		
	}
	
	public List<PersonaDTO> search(String filtro) throws Exception {
		try {
			//List<Persona> personas = repository.findByNombreContainingOrApellidoContaining(filtro, filtro);
			List<PersonaDTO> personas = daoPersona.buscarPorNombreOApellido(filtro);
			return personas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	public PersonaDTO searchByDni(int dni) throws Exception {
		try {
			return daoPersona.buscarPorDni(dni);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void asignarLibro(Long idPersona, Long idLibro) throws Exception {
		try {
			
			LibroDTO libro = svcLibro.findById(idLibro);
			if (libro == null) throw new Exception("Libro no encontrado");
			
			if (libro.getEstado() == EstadoLibro.OCUPADO) {
				throw new Exception("El libro ha sido asignado a otra persona");
			}
			
			PersonaDTO persona = findById(idPersona);
			if (persona == null) throw new Exception("Persona no encontrada");

			
			persona.getLibros().add(libro);
			
			libro.setEstado(EstadoLibro.OCUPADO);
					
			update(idPersona, persona);
			svcLibro.update(idLibro, libro);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	/*@Override
	public Page<PersonaDTO> search(String filtro, Pageable pageable) throws Exception {
		try {
			//Page<Persona> personas = repository.findByNombreContainingOrApellidoContaining(filtro, filtro, pageable);
			Page<PersonaDTO> personas = daoPersona.search(filtro, pageable);
			return personas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}*/

}
