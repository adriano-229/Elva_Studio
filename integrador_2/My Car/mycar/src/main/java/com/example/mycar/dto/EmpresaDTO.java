package com.example.mycar.dto;

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
