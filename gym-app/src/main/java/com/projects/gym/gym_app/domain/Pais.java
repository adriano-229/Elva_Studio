package com.projects.gym.gym_app.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor @AllArgsConstructor
public class Pais {
    @Id
    private String id;
    private String nombre;
    private boolean eliminado;

}
