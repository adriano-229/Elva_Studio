package com.projects.gym.gym_app.service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SocioFormDTO {
    private String id;

    // Persona
    @NotBlank private String nombre;
    @NotBlank private String apellido;
    @NotNull  private LocalDate fechaNacimiento;
    @NotBlank private String tipoDocumento;   // Enum en String
    @NotBlank private String numeroDocumento;
    @Pattern(regexp="\\+?[0-9\\- ]{6,20}") private String telefono;
    @Email private String correoElectronico;

    // Socio
    @NotNull  private Long numeroSocio;
    @NotBlank private String sucursalId;
    private String sucursalNombre;

    // Usuario (composición)
    @NotBlank private String nombreUsuario;
    @NotBlank private String clave;
    @NotBlank private String rol;            // Enum en String

    // Direccion (usamos creación inline)
    private String direccionId;              // opcional: seleccionar existente
    @NotBlank private String calle;
    @NotBlank private String numeracion;
    private String barrio;
    private String manzanaPiso;
    private String casaDepartamento;
    private String referencia;
    @NotBlank private String localidadId;    // select en cascada

    private boolean eliminado;

}
