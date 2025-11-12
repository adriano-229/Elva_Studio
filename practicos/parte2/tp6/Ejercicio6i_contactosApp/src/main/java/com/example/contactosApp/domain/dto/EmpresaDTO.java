package com.example.contactosApp.domain.dto;

import com.example.contactosApp.domain.Base;

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
public class EmpresaDTO extends BaseDTO{
	
	private String nombre;
	//private ContactoDTO contacto;
	
}
