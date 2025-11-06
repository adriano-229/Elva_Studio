package com.projects.gym.gym_app.domain;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor
@ToString(exclude = {"rutinas", "direccion", "usuario"})
@Entity
@PrimaryKeyJoinColumn(name = "persona_id")
public class Socio extends Persona {
    @Column(nullable = false, unique = true)
    private Long numeroSocio;

    @OneToOne(cascade=CascadeType.ALL, orphanRemoval=true, optional=false)
    @JoinColumn(name="usuario_id", nullable=false)
    private Usuario usuario;

    @ManyToOne(optional=false, fetch=FetchType.LAZY)
    @JoinColumn(name="direccion_id", nullable=false)
    private Direccion direccion;

    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL)
    private List<Rutina> rutinas;
}
