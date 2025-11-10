package com.example.mycar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LocalidadDTO extends BaseDTO {

    @NotBlank(message = "El nombre de la dirección no puede estar vacío")
    @Size(min = 3, max = 50, message = "El nombre de la localidad debe tener entre 3 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El código postal no puede estar vacío")
    @Pattern(regexp = "\\d{4,10}", message = "El código postal debe contener solo números y tener entre 4 y 10 dígitos")
    private String codigoPostal;

    @NotNull(message = "El departamento es obligatorio")
    private Long departamentoId;

    @Builder.Default
    @Valid
    private List<DireccionDTO> direcciones = new ArrayList<>();

}
