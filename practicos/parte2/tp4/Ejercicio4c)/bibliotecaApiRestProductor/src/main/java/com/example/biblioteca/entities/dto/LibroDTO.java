package com.example.biblioteca.entities.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.biblioteca.entities.Autor;
import com.example.biblioteca.entities.enums.EstadoLibro;
import com.example.biblioteca.entities.enums.TipoLibro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibroDTO {
	private Long id;
	private boolean activo;
	private EstadoLibro estado;
	private int fecha;
	private String genero;
	private int paginas;
	private String titulo;
	private TipoLibro tipo;
	private int cantEjemplares;
	private List<Autor> autores = new ArrayList<Autor>();
	private String rutaPdf;
	private MultipartFile archivoPdf;

}
