package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.TipoImagen;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class ImagenDTO extends BaseDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El tipo MIME no puede estar vacío")
    private String mime;

    @NotNull(message = "El contenido no puede ser nulo")
    private byte[] contenido;

    private String ruta;

    @NotNull(message = "El tipo de imagen es obligatorio")
    private TipoImagen tipoImagen;
}
