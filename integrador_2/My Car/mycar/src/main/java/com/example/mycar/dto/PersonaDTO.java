package com.example.mycar.dto;

import java.time.LocalDate;

import com.example.mycar.enums.TipoDocumento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PersonaDTO extends BaseDTO{
	
	private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private ImagenDTO imagen;
    private ContactoDTO contacto;
    private DireccionDTO direccion;
	

}
