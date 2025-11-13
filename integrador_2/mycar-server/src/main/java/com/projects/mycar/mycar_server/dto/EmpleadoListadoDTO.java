package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.TipoEmpleado;
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
