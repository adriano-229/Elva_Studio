package com.projects.mycar.mycar_server.dto;

import com.projects.mycar.mycar_server.enums.TipoDocumento;
import com.projects.mycar.mycar_server.enums.TipoEmpleado;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmpleadoFormDTO {
    private Long id;

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotNull
    private LocalDate fechaNacimiento;

    @NotNull
    private TipoDocumento tipoDocumento;

    @NotBlank
    private String numeroDocumento;

    @Pattern(regexp = "\\+?[0-9\\- ]{6,20}", message = "Teléfono inválido")
    private String telefono;

    @Email
    private String correoElectronico;

    @NotNull
    private TipoEmpleado tipo;

    @NotBlank
    private String rol;
    private boolean activo = true;

    private String usuarioId;

    @NotBlank
    private String nombreUsuario;

    private String clave;

    // Direccion (usamos creación inline)
    private String direccionId;              // opcional: seleccionar existente
    @NotBlank
    private String calle;
    @NotBlank
    private String numeracion;
    private String barrio;
    private String manzanaPiso;
    private String casaDepartamento;
    private String referencia;
    @NotBlank
    private String localidadId;    // select en cascada
}
