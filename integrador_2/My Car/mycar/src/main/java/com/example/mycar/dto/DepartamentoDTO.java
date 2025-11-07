package com.example.mycar.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DepartamentoDTO extends BaseDTO{
	
	private String nombre;
	private ProvinciaDTO provincia;
	private List<LocalidadDTO> localidades;

}
