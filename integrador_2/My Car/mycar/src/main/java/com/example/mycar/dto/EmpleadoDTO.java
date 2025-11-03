package com.example.mycar.dto;

import com.example.mycar.enums.TipoEmpleado;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmpleadoDTO {
    Long id;
    String nombreCompleto;
    TipoEmpleado tipo;
    boolean activo;
}
