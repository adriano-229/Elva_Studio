package com.projects.mycar.mycar_admin.domain;

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
public class ClienteDTO extends PersonaDTO {
    private String direccionEstadia;
    private NacionalidadDTO nacionalidad;
}
