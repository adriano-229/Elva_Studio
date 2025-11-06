package com.projects.gym.gym_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Localidad {
    @Id
    private String id;
    private String nombre;
    private String codigoPostal;
    @ManyToOne
    private Departamento departamento;
    private boolean eliminado;
}
