package com.projects.mycar.mycar_cliente.domain;

import com.projects.mycar.mycar_cliente.domain.enums.TipoContacto;
import com.projects.mycar.mycar_cliente.domain.enums.TipoTelefono;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
public class ContactoTelefonicoDTO extends BaseDTO {

    //contacto
    private TipoContacto tipoContacto;

    //telefono
    private String telefono;
    private TipoTelefono tipoTelefono;

}
