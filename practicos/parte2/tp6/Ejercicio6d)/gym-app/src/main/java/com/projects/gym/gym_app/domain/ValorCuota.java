package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.Mes;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "valor_cuota")
public class ValorCuota {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Mes mes;

    @Column(nullable = false)
    private Integer anio;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorCuota;

    @Column(name = "creado_el", nullable = false)
    private LocalDateTime creadoEl;

    @PrePersist
    public void prePersist() {
        if (creadoEl == null) {
            creadoEl = LocalDateTime.now();
        }
    }
}
