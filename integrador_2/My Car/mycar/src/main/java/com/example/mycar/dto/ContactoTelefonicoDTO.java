package com.example.mycar.dto;
import com.example.mycar.enums.TipoTelefono;

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
public class ContactoTelefonicoDTO extends ContactoDTO{
	
	private String telefono;
	private TipoTelefono tipoTelefono;

}
