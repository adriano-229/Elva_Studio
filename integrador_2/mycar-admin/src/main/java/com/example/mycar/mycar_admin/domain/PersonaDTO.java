package com.example.mycar.mycar_admin.domain;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

import com.example.mycar.mycar_admin.domain.enums.TipoDocumento;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonaDTO extends BaseDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 50, message = "El apellido debe tener entre 2 y 50 caracteres")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El tipo de documento es obligatorio")
    private TipoDocumento tipoDocumento;

    @NotBlank(message = "El número de documento no puede estar vacío")
    @Size(min = 6, max = 12, message = "El número de documento debe tener entre 6 y 12 caracteres")
    @Pattern(regexp = "\\d+", message = "El número de documento solo puede contener números")
    private String numeroDocumento;

    @NotNull(message = "La imagen no puede ser nula")
    private ImagenDTO imagen;

    @NotNull(message = "El contacto no puede ser nulo")
    private ContactoDTO contacto;

    @NotNull(message = "La dirección no puede ser nula")
    private DireccionDTO direccion;

}
