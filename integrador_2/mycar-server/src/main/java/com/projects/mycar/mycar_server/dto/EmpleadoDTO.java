package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.TipoEmpleado;
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
