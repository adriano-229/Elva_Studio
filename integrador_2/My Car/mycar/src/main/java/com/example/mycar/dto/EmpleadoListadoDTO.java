package com.example.mycar.dto;

import com.example.mycar.enums.TipoEmpleado;
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
