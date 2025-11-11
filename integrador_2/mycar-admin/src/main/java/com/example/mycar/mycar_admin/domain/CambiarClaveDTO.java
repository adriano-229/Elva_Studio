package com.example.mycar.mycar_admin.domain;

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
