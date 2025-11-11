package com.example.mycar.mycar_admin.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmpresaDTO {
    String id;
    String nombre;
    String telefono;
    String correoElectronico;
    boolean eliminado;
}
