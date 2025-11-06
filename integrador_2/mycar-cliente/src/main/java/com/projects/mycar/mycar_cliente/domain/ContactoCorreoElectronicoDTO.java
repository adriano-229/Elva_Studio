package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.TipoContacto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactoCorreoElectronicoDTO extends BaseDTO {

    //contacto
    private TipoContacto tipoContacto;

    //correo
    private String email;

}
