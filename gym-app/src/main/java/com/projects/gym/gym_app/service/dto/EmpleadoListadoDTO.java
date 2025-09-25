package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmpleadoListadoDTO {
    Long id;
    String nombreCompleto;
    String nombreUsuario;
    TipoEmpleado tipo;
    boolean activo;
    String telefono;
    String correoElectronico;
    String sucursalNombre;
}
