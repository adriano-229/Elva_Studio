package com.projects.mycar.mycar_server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CambiarClaveDTO {

    private String claveActual;
    private String claveNueva;
    private String confirmarClave;
}
