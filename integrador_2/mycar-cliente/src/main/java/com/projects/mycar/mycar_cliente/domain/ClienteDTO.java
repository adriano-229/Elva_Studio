package com.projects.mycar.mycar_cliente.domain;

import java.time.LocalDate;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
@EqualsAndHashCode(callSuper = true)
public class ClienteDTO extends BaseDTO{
	
    // Persona
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String tipoDocumento;   // Enum en String
    private String numeroDocumento;
    private String direccionEstadia;
    private Long imagenId;
    private Long alquilerId;
    

}
