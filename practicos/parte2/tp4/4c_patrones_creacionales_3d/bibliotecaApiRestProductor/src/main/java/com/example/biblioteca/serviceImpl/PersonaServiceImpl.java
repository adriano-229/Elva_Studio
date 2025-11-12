package com.example.biblioteca.serviceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.entities.Persona;
import com.example.biblioteca.entities.enums.EstadoLibro;
import com.example.biblioteca.error.EntidadRelacionadaException;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.PersonaRepository;
import com.example.biblioteca.service.PersonaService;

import jakarta.transaction.Transactional;


@Service
public class PersonaServiceImpl extends BaseServiceImpl<Persona, Long> implements PersonaService{
	
	@Autowired
	private PersonaRepository repository;
	
	@Autowired
	private LibroRepository libroRepository;
	
	public PersonaServiceImpl(BaseRepository<Persona, Long> baseRepository) {
		super(baseRepository);
	}
	
	public void validar(Persona persona) throws Exception{
		//una persona puede no tener libros asociados 
	}
	
	@Override
	@Transactional
	public Persona save(Persona persona) throws Exception {
		try {
			
			validar(persona);
			
	    	Optional<Persona> opt = repository.findByDniAndActivoTrue(persona.getDni());
	    	if (opt.isPresent()) {
	         throw new Exception("Existe una persona con el dni indicado");
	    	} 
            
			return baseRepository.save(persona);
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Persona update(Long id, Persona personaNuevo) throws Exception {
		try {
			
			validar(personaNuevo);
			
			Persona persona = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Persona no encontrada"));
			
			persona.setNombre(personaNuevo.getNombre());
			persona.setApellido(personaNuevo.getApellido());
			persona.setDni(personaNuevo.getDni());
			persona.setDomicilio(personaNuevo.getDomicilio());
			persona.setLibros(personaNuevo.getLibros());
			persona.setActivo(true);
			
			// Actualizamos los libros manualmente pq libros son mapeados a la entidad Libro y no a sus clases especificas
	        Set<Libro> librosActualizados = new HashSet<>();
	        for (Libro libro : personaNuevo.getLibros()) {
	            if (libro.getId() != null) {
	                Libro libroExistente = libroRepository.findById(libro.getId())
	                        .orElseThrow(() -> new Exception("Libro no encontrado: " + libro.getId()));
	                libroExistente.setEstado(EstadoLibro.OCUPADO);
	                librosActualizados.add(libroExistente);
	            } 
	        }
	        persona.setLibros(librosActualizados);

				
			return baseRepository.save(persona);
			
		
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error al actualizar persona", e);
		}
	}

	@Override
	@Transactional
	public List<Persona> search(String filtro) throws Exception {
		try {
			//List<Persona> personas = repository.findByNombreContainingOrApellidoContaining(filtro, filtro);
			List<Persona> personas = repository.search(filtro);
			return personas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Optional<Persona> searchByDni(int dni) throws Exception {
		try {
			return repository.findByDniAndActivoTrue(dni);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Page<Persona> search(String filtro, Pageable pageable) throws Exception {
		try {
			//Page<Persona> personas = repository.findByNombreContainingOrApellidoContaining(filtro, filtro, pageable);
			Page<Persona> personas = repository.search(filtro, pageable);
			return personas;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	
	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			
			Persona persona = baseRepository.findByIdAndActivoTrue(id).
					orElseThrow(() -> new Exception("Persona no encontrada"));
			
			if(!persona.getLibros().isEmpty()) {
			    throw new EntidadRelacionadaException("No se puede eliminar la persona porque tiene libros asociados");
			}

			persona.setActivo(false);
			return true;
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

}
