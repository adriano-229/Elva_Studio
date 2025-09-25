// src/main/java/com/projects/gym/gym_app/domain/DetalleFactura.java
package com.projects.gym.gym_app.domain;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "detalle_factura")
public class DetalleFactura {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "factura_id", nullable = false)
    private Factura factura;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cuota_mensual_id", nullable = false)
    private CuotaMensual cuotaMensual;

    // Importe "congelado" en el detalle (recomendado)
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal importe = BigDecimal.ZERO;

    @Column(nullable = false)
    private boolean eliminado;
}
