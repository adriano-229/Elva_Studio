package com.projects.gym.gym_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Departamento {
    @Id
    private String id;
    private String nombre;
    @ManyToOne
    private Provincia provincia;
    private boolean eliminado;
}
