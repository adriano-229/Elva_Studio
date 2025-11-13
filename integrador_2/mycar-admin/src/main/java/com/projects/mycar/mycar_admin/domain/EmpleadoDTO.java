package com.projects.mycar.mycar_admin.domain;

import com.projects.mycar.mycar_admin.domain.enums.TipoEmpleado;
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
