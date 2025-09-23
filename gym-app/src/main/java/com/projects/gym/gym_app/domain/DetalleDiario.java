package com.projects.gym.gym_app.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleDiario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int numeroDia;
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "rutina_id")
    private Rutina rutina;

    @OneToMany(mappedBy = "detalleDiario", cascade = CascadeType.ALL)
    private List<DetalleEjercicio> detalleEjercicios;
}
