package com.example.mycar.dto;

import com.example.mycar.enums.TipoContacto;

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
public class ContactoDTO extends BaseDTO{
	
	private TipoContacto tipoContacto;
	private String observacion;

}
