package com.projects.mycar.mycar_server.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpresaFormDTO {

    private String id;

    @NotBlank
    @Size(max = 150)
    private String nombre;

    @Size(max = 30)
    private String telefono;

    @Email
    @Size(max = 120)
    private String correoElectronico;
}
