package com.example.contactosApp.domain.dto;

import com.example.contactosApp.domain.enums.TipoTelefono;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ContactoTelefonicoDTO extends ContactoDTO{
	
	private String telefono;
	private TipoTelefono tipoTelefono;
}
