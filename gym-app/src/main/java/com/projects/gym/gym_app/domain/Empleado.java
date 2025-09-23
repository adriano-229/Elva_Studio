package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
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

    private boolean activo;

    @Enumerated(EnumType.STRING)
    private TipoEmpleado tipo;
}
