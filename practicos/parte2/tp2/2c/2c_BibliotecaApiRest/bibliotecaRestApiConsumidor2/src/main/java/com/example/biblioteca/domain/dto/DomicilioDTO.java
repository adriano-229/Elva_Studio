package com.example.biblioteca.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DomicilioDTO extends BaseDTO{
	
	private String calle;
	
	private int numero;
	
	private LocalidadDTO localidad;
}
