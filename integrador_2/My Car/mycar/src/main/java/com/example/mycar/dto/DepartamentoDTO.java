package com.example.mycar.dto;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class DepartamentoDTO extends BaseDTO{
	
	@NotBlank(message = "El nombre del departamento no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del departamento debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "La provincia es obligatoria")
    private Long provinciaId;
    
    @Builder.Default
    @Valid
    private Set<LocalidadDTO> localidades = new HashSet<>();

}
