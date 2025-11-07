package com.projects.gym.gym_app.domain;

import java.time.LocalDate;
import java.util.List;

import com.projects.gym.gym_app.domain.enums.EstadoRutina;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Rutina {
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicia;
    private LocalDate fechaFinaliza;
    private String objetivo;
    private boolean activo;

    @Enumerated(EnumType.STRING)
    private EstadoRutina estadoRutina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id")
    private Socio socio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profesor_id")
    private Empleado profesor;

    @OneToMany(mappedBy = "rutina", cascade = CascadeType.ALL)
    private List<DetalleDiario> detallesDiarios;
}
