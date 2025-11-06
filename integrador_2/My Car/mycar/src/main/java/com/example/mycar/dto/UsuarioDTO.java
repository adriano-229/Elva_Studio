package com.example.mycar.dto;

import com.example.mycar.enums.RolUsuario;
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
public class UsuarioDTO extends BaseDTO {

    String nombreUsuario;
    String clave;
    RolUsuario rol;

}
