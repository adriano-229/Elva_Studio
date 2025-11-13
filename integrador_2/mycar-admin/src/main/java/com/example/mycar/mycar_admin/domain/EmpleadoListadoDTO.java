package com.example.mycar.mycar_admin.domain;

import com.example.mycar.mycar_admin.domain.enums.TipoEmpleado;
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
