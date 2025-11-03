package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.TipoEmpleado;
import com.projects.gym.gym_app.domain.Usuario;
import com.projects.gym.gym_app.domain.Direccion;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.FetchType;
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

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}
