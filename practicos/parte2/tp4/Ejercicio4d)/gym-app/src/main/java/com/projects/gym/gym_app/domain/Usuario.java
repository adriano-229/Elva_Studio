package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "nombre_usuario", nullable = false, unique = true, length = 50)
    private String nombreUsuario;
    @Column(nullable = false)
    private String clave;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    private boolean eliminado;
}
