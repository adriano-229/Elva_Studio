package com.example.mycar.dto;

import com.example.mycar.enums.RolUsuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UsuarioDTO extends BaseDTO {

    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(min = 4, max = 10, message = "El nombre de usuario debe tener entre 4 y 10 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La clave no puede estar vacía")
    @Size(min = 8, max = 15, message = "La clave debe tener entre 8 y 15 caracteres")
    private String clave;

    @NotNull(message = "El rol del usuario es obligatorio")
    private RolUsuario rol;

    @NotNull(message = "Debe haber una persona asociada al usuario")
    private PersonaDTO persona;;
    

}
