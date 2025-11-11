package com.example.mycar.mycar_admin.domain;

import com.example.mycar.mycar_admin.domain.enums.TipoContacto;

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
public class ContactoDTO extends BaseDTO {

    @NotNull(message = "El tipo de contacto es obligatorio")
    private TipoContacto tipoContacto;

    @NotBlank(message = "La observación no puede estar vacía")
    @Size(max = 255, message = "La observación no puede tener más de 255 caracteres")
    private String observacion;

}
