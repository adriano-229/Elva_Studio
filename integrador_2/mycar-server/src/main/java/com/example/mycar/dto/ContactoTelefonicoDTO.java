package com.example.mycar.dto;

import com.example.mycar.enums.TipoTelefono;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class ContactoTelefonicoDTO extends ContactoDTO {

    @NotBlank(message = "El número de teléfono no puede estar vacío")
    @Pattern(regexp = "\\+?\\d{6,15}", message = "El teléfono debe contener solo números y puede incluir un prefijo con '+'")
    @Size(min = 6, max = 15, message = "El teléfono debe tener entre 6 y 15 dígitos")
    private String telefono;

    @NotNull(message = "El tipo de teléfono es obligatorio")
    private TipoTelefono tipoTelefono;

}
