package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
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
