package com.projects.gym.gym_app.service.dto;

import com.projects.gym.gym_app.domain.enums.TipoDocumento;
import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
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

    @NotBlank
    private String sucursalId;

    private String sucursalNombre;

    @NotNull
    private TipoEmpleado tipo;

    private boolean activo = true;

    private String usuarioId;

    @NotBlank
    private String nombreUsuario;

    private String clave;
    private String direccionId;

    @NotBlank
    private String calle;

    @NotBlank
    private String numeracion;

    private String barrio;

    private String manzanaPiso;

    private String casaDepartamento;

    private String referencia;

    @NotBlank
    private String localidadId;

    @NotBlank
    private String rol;

}
