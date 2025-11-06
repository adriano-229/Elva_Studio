package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.TipoPago;
import jakarta.persistence.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "forma_de_pago")
public class FormaDePago {
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pago", nullable = false, length = 30)
    private TipoPago tipoPago;

    @Column(length = 255)
    private String observacion;

    @Column(nullable = false)
    private boolean eliminado;
}
