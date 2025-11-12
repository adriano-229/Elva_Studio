package com.example.biblioteca.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AutorDTO{
	
	private Long id;
	private boolean activo;
	private String nombre;
	private String apellido;
	private String biografia;

}

