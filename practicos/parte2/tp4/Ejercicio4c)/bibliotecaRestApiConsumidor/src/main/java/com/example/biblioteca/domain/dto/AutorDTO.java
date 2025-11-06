package com.example.biblioteca.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AutorDTO extends BaseDTO{
	
	private String nombre;
	
	private String apellido;
	
	private String biografia;

}
