package com.example.mycar.mycar_admin.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ProvinciaDTO extends BaseDTO {

    @NotBlank(message = "El nombre de la provincia no puede estar vacío")
    @Size(min = 3, max = 100, message = "El nombre de la provincia debe tener entre 3 y 100 caracteres")
    private String nombre;

    @NotNull(message = "El país es obligatorio")
    private Long paisId;

    @Builder.Default
    @Valid
    private Set<DepartamentoDTO> departamentos = new HashSet<>();

}
