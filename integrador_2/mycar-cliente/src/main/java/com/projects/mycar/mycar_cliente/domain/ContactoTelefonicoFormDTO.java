package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.TipoContacto;
import com.projects.mycar.mycar_cliente.domain.enums.TipoTelefono;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactoTelefonicoFormDTO extends BaseDTO {

    // contacto
    private Long id; // opcional para creación

    @NotNull(message = "El tipo de contacto es obligatorio")
    private TipoContacto tipoContacto;

    private boolean eliminado;

    // teléfono
    @NotBlank(message = "El teléfono no puede estar vacío")
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "El teléfono debe contener solo números y opcional '+' al inicio")
    private String telefono;

    @NotNull(message = "El tipo de teléfono es obligatorio")
    private TipoTelefono tipoTelefono;

}
