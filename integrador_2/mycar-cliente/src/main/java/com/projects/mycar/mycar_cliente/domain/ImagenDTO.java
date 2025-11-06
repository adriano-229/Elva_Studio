package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.TipoImagen;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class ImagenDTO extends BaseDTO {
    private String nombre;
    private String mime;
    private String url;
    private TipoImagen tipoImagen;


}
