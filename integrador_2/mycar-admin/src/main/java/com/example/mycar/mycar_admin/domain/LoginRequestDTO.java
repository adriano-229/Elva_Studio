package com.example.mycar.mycar_admin.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    private String nombreUsuario;
    private String clave;
}
