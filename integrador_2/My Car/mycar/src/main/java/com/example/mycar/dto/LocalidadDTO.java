package com.example.mycar.dto;

import java.util.List;

import com.example.mycar.entities.Departamento;
import com.example.mycar.entities.Direccion;

import jakarta.validation.constraints.NotBlank;
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
public class LocalidadDTO extends BaseDTO{
	
	private String nombre;
	private String codigoPostal;
	@NotBlank
	private DepartamentoDTO departamento;
	private List<Direccion> direcciones;

}
