package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.GrupoMuscular;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DetalleEjercicio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String actividad;
    private int series;
    private int repeticiones;
    private boolean activo;

    @Enumerated(EnumType.STRING)
    private GrupoMuscular grupoMuscular;

    @ManyToOne
    @JoinColumn(name = "detalle_diario_id")
    private DetalleDiario detalleDiario;
}
