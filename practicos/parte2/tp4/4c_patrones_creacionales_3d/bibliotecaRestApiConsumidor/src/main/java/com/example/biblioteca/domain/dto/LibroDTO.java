package com.example.biblioteca.domain.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.domain.enums.EstadoLibro;
import com.example.biblioteca.domain.enums.TipoLibro;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO extends BaseDTO{
	
	private int fecha;
	
	private String genero;
	
	private int paginas;
	
	private String titulo;
	
	private List<AutorDTO> autores = new ArrayList<AutorDTO>();
	
	private String rutaPdf;
	
	private TipoLibro tipo;
	
	private int cantEjemplares;
	
	@JsonIgnore
	private List<Long> autoresIds = new ArrayList<Long>();
	
	private EstadoLibro estado = EstadoLibro.DISPONIBLE;
	
	@JsonIgnore
	private MultipartFile pdf;
	
}
