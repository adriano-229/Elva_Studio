package com.example.biblioteca.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.error.EntidadRelacionadaException;
import com.example.biblioteca.repository.AutorRepository;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.service.AutorService;

import jakarta.transaction.Transactional;


@Service
public class AutorServiceImpl extends BaseServiceImpl<Autor, Long> implements AutorService{
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LibroRepository libroRepository;
	
	public AutorServiceImpl(BaseRepository<Autor, Long> baseRepository) {
		super(baseRepository);
	}
	
	public void validar(Autor autor) throws Exception {
		
	}
	
	@Override
	@Transactional
	public Autor save(Autor autor) throws Exception {
		try {
			
			validar(autor);
			
			/*-------------------------------------------------------------------------------
			//No valido que el autor ya exista porque no tengo otro atributo unico mas que el id
			
	    	Autor autorAux = autorRepository.findByDniAndActivotrue(dni);
	    	if (autorAux != null) {
	         throw new Exception("El autor ya existe");
	    	}
			//---------------------------------------------------------------------------------*/
            
			return baseRepository.save(autor);
		
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Autor update(Long id, Autor autorNuevo) throws Exception {
		try {
			
			validar(autorNuevo);
			
			Autor autor = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Autor no encontrado"));
			
			autor.setNombre(autorNuevo.getNombre());
			autor.setApellido(autorNuevo.getApellido());
			autor.setBiografia(autorNuevo.getBiografia());
				
			return baseRepository.save(autor);
			
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar autor", e);
		}
	}
	
	@Override
	@Transactional
	public boolean delete(Long id) throws Exception {
		try {
			
			if (baseRepository.existsById(id)) {
				
				Autor autor = baseRepository.findByIdAndActivoTrue(id).
								orElseThrow(() -> new Exception("Autor no encontrado"));
						
				
				if (libroRepository.existsByAutores_Id(id)) {
					throw new EntidadRelacionadaException("No se puede eliminar el autor pq hay un libro asociado");
				}
				
				autor.setActivo(false);
				return true;
				
			} else {
				throw new Exception();
			}
			
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}

	@Override
	public List<Autor> searchByNombreApellido(String filtro) throws Exception {
		
		try {
			return autorRepository.findByFiltro(filtro);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		
	}
	

}
