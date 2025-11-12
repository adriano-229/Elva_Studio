package com.example.biblioteca.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.error.EntidadRelacionadaException;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.PersonaRepository;
import com.example.biblioteca.service.LibroService;

import jakarta.transaction.Transactional;

@Service
public class LibroServiceImpl extends BaseServiceImpl<Libro, Long> implements LibroService{
	
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private LibroRepository libroRepository;
	
	public LibroServiceImpl(BaseRepository<Libro, Long> baseRepository) {
		super(baseRepository);
	}
	
	public void validar(Libro libro) throws Exception {
		//Si o si deber tener autores asociados
	}
	
	@Override
	@Transactional
	public Libro save(Libro libro) throws Exception {
		try {
			
			validar(libro);
			
	    	Optional<Libro> opt = libroRepository.findByTituloIgnoreCaseAndActivoTrue(libro.getTitulo());
	    	
	    	if ( !opt.isPresent()) {
	    		
	    		return baseRepository.save(libro);
				
	    	}
	    	
	    	return opt.get();
			
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	@Transactional
	public Libro update(Long id, Libro libro) throws Exception {
		try {
			
			validar(libro);
			
			baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Libro no encontrado"));
			
			/*libroNuevo.setId(id);
    		libro.setGenero(libroNuevo.getGenero());
    		libro.setPaginas(libroNuevo.getPaginas());
    		libro.setTitulo(libroNuevo.getTitulo());
    		libro.setAutores(libroNuevo.getAutores());*/
			
				
			return baseRepository.save(libro);
			
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar libro", e);
		}
	}

	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			
			if (baseRepository.existsById(id)) {
				
				Libro libro = baseRepository.findByIdAndActivoTrue(id).
								orElseThrow(() -> new Exception("Libro no encontrado"));
						
				
				if (personaRepository.existsByLibros_Id(id)) {
					throw new EntidadRelacionadaException("No se puede eliminar el libro pq hay una persona asociada");
				}
				
				libro.setActivo(false);
				return true;
				
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Libro> buscar(String filtro) throws Exception {
		
		try {
			List<Libro> libros = libroRepository.buscar(filtro);
			return libros;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Libro> buscarPorAutor(String filtro) throws Exception {
		
		try {
			List<Libro> libros = libroRepository.buscarPorAutor(filtro);
			return libros;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public List<Libro> buscarPorTituloOGenero(String filtro) throws Exception {
		
		try {
			return libroRepository.buscarPorTituloOGenero(filtro);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Override
	public Optional<Libro> buscarPorTitulo(String titulo) throws Exception {
		try {
			return libroRepository.findByTituloIgnoreCaseAndActivoTrue(titulo);
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
