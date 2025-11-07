package com.example.mycar.dto;

import java.util.ArrayList;
import java.util.List;

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
public class PaisDTO extends BaseDTO{
	
	private String nombre;
	
	@Builder.Default
	private List<ProvinciaDTO> provincias = new ArrayList<>();

}
