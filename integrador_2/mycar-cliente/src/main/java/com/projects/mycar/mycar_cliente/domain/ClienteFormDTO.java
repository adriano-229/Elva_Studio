package com.projects.mycar.mycar_cliente.domain;

import java.time.LocalDate;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class ClienteFormDTO extends BaseDTO{
	
	// usuario
    private Long id; // opcional para edición

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 50, message = "El nombre de usuario debe tener entre 3 y 50 caracteres")
    private String nombreUsuario;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String clave;

    private boolean eliminado;

    // persona
    
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento; // Enum representado como String

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "La dirección de estadía es obligatoria")
    private String direccionEstadia;

    // cliente 
    
    private Long imagenId;  // referencia a la foto del cliente
    private Long alquilerId; // último alquiler o actual

    private MultipartFile foto; // opcional, si se sube desde el formulario
	

}
