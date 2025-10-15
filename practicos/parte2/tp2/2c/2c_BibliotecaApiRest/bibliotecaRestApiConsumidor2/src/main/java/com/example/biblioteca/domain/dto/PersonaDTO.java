package com.example.biblioteca.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaDTO extends BaseDTO{
	
	private String nombre;
	
	private String apellido;
	
	private int dni;
	
	private DomicilioDTO domicilio;
	
	private List<LibroDTO> libros = new ArrayList<LibroDTO>();
	
}
