package com.example.contactosApp.domain.dto;
import com.example.contactosApp.domain.enums.TipoContacto;

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
public abstract class ContactoDTO extends BaseDTO{
	
	private TipoContacto tipoContacto;
	private String observacion;
	private EmpresaDTO empresa;
	private PersonaDTO persona;
	private String clase;
}
