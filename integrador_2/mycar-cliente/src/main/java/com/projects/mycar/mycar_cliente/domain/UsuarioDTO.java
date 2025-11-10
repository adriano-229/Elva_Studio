package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.Rol;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioDTO extends BaseDTO {
    private String nombreUusario;
    private String clave;
    private Rol rol;


}
