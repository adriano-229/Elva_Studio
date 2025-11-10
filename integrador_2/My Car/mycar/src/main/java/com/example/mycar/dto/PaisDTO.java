package com.example.mycar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PaisDTO extends BaseDTO {

    @NotBlank(message = "El nombre del país no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre del país debe tener entre 3 y 100 caracteres")
    private String nombre;

    @Builder.Default
    @Valid
    private Set<ProvinciaDTO> provincias = new HashSet<>();

}
