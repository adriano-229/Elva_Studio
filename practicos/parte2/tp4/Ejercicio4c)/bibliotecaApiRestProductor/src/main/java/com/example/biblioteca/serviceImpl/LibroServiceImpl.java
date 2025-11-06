package com.example.biblioteca.serviceImpl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.entities.Libro;
import com.example.biblioteca.entities.LibroDigital;
import com.example.biblioteca.entities.LibroFisico;
import com.example.biblioteca.entities.dto.LibroDTO;
import com.example.biblioteca.error.EntidadRelacionadaException;
import com.example.biblioteca.repository.BaseRepository;
import com.example.biblioteca.repository.LibroRepository;
import com.example.biblioteca.repository.PersonaRepository;
import com.example.biblioteca.service.LibroService;

import jakarta.transaction.Transactional;

@Service
public class LibroServiceImpl extends BaseServiceImpl<Libro, Long> implements LibroService{
	
	@Value("${app.upload.dir}")
	private String uploadDir;
	
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
			
			if (opt.isPresent() && opt.get().getTipo() == libro.getTipo()) {
				
				throw new Exception("Ya existe un libro " + libro.getTipo() + " con el título '" + libro.getTitulo() + "'.");
			}
			
			System.out.println("REPO LIBRO: " + libro.getTitulo() + ", tipo: " + libro.getTipo() + ", páginas: " + libro.getPaginas());

	    	return baseRepository.save(libro);
	    	
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public Libro update(Long id, Libro libro) throws Exception {
		try {
			
			validar(libro);
			
			Libro libroActual = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Libro no encontrado"));
			
			libroActual = Libro.builder()
					.fecha(libro.getFecha())
					.genero(libro.getGenero())
					.paginas(libro.getPaginas())
					.titulo(libro.getTitulo())
					.autores(libro.getAutores())
					.build();
			
			return baseRepository.save(libroActual);
			
		
		} catch (Exception e) {
			throw new Exception("Error al actualizar libro", e);
		}
	}
	
	@Transactional
	public Libro update(Long id, LibroDTO libroDto) throws Exception {
		try {
			
			Libro libro = baseRepository.findByIdAndActivoTrue(id)
		            .orElseThrow(() -> new Exception("Libro no encontrado"));
			
			libro.setTitulo(libroDto.getTitulo());
	        libro.setGenero(libroDto.getGenero());
	        libro.setFecha(libroDto.getFecha());
	        libro.setPaginas(libroDto.getPaginas());
	        libro.setAutores(libroDto.getAutores()); 
	        
			if (libro instanceof LibroDigital digital) {
				
				if (libroDto.getArchivoPdf() != null && !libroDto.getArchivoPdf().isEmpty()) {
					String ruta = almacenarPdf(libroDto.getArchivoPdf());
			        digital.setRutaPdf(ruta);
				}
			}else if (libro instanceof LibroFisico fisico) {
				fisico.setCantEjemplares(libroDto.getCantEjemplares());
			}
			
			System.out.println("UPDATE PRODUCTOR: " + libro);
			
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
	
	public String almacenarPdf(MultipartFile pdf) throws Exception {
		try {
	        // Guardar archivo en carpeta local
	        String rutaDestino = uploadDir; 
	        File carpeta = new File(rutaDestino);
	        if (!carpeta.exists()) carpeta.mkdirs();
	        
	        String nombreArchivo = System.currentTimeMillis() + "_" + pdf.getOriginalFilename();

	        Path pathDestino = Paths.get(rutaDestino, nombreArchivo);
	        
	        pdf.transferTo(pathDestino.toFile());
	        
	        return pathDestino.toString();

	    } catch (Exception e) {
	        e.printStackTrace();
	        throw new Exception("Error al subir archivo");
	       
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
