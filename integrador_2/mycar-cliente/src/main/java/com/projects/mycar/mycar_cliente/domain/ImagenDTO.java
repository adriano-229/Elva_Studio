package com.projects.mycar.mycar_cliente.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.projects.mycar.mycar_cliente.domain.enums.TipoImagen;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImagenDTO extends BaseDTO {

    private String nombre;

    private String mime;

    private byte[] contenido;

    private String ruta;

}
