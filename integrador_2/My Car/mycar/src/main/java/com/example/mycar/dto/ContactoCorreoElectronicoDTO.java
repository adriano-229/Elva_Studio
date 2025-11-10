package com.example.mycar.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class ContactoCorreoElectronicoDTO extends ContactoDTO {

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ingresar un correo electrónico válido")
    @Size(max = 100, message = "El email no puede tener más de 100 caracteres")
    private String email;


}
