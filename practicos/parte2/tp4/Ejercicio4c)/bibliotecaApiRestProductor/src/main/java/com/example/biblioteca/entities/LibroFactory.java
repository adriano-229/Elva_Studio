package com.example.biblioteca.entities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.biblioteca.entities.dto.LibroDTO;
import com.example.biblioteca.serviceImpl.LibroServiceImpl;

@Component
public class LibroFactory {
	
	@Autowired
	private LibroServiceImpl libroService;
	
	public Libro crearLibro(LibroDTO libro) throws Exception{
		
		if (libro == null) {
			throw new Exception("Ojo que libro dto esta vacio, al crear el libro en el productor");
		}
		
		switch(libro.getTipo()) {
			case FISICO:
				return LibroFisico.builder()
						.titulo(libro.getTitulo())
						.genero(libro.getGenero())
						.paginas(libro.getPaginas())
						.fecha(libro.getFecha())
						.tipo(libro.getTipo())
						.autores(libro.getAutores())
						.cantEjemplares(libro.getCantEjemplares())
						.activo(libro.isActivo())
						.estado(libro.getEstado())
						.build();
			case DIGITAL:
				
				if (libro.getArchivoPdf() == null) {
					throw new Exception("El archivo pdf esta vacio");
				}
				
				String ruta = libroService.almacenarPdf(libro.getArchivoPdf());
				
				
				return LibroDigital.builder()
						.titulo(libro.getTitulo())
						.genero(libro.getGenero())
						.paginas(libro.getPaginas())
						.fecha(libro.getFecha())
						.tipo(libro.getTipo())
						.autores(libro.getAutores())
						.rutaPdf(ruta)
						.activo(libro.isActivo())
						.build();
			default:
				throw new Exception("Tipo de libro desconocido: " + libro.getTipo());
		}
		
	}
	
	

}
