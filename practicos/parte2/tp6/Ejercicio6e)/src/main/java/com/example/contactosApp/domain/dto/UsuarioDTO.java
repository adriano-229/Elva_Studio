package com.example.contactosApp.domain.dto;

import com.example.contactosApp.domain.enums.Rol;

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
public class UsuarioDTO extends BaseDTO{
	
	private String cuenta;
	private String clave;
	private PersonaDTO persona;
	private Rol rol;

}
