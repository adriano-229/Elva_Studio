package com.projects.mycar.mycar_server.entities;


import com.projects.mycar.mycar_server.enums.TipoEmpleado;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
@Getter
@Setter
@NoArgsConstructor
public class Empleado extends Persona {

    @Enumerated(EnumType.STRING)
    private TipoEmpleado tipo;

}