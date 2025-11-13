package com.projects.mycar.mycar_server.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String nombreUsuario;
    private String clave;
}
