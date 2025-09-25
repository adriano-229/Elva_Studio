package com.projects.gym.gym_app.domain;

import com.projects.gym.gym_app.domain.enums.EstadoFactura;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Entity @Table(name = "factura")
public class Factura {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "numero_factura", nullable = false)
    private long numeroFactura;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDate fechaFactura;

    @Column(name = "total_pagado", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPagado = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoFactura estado = EstadoFactura.SIN_DEFINIR;

    @Column(nullable = false)
    private boolean eliminado = false;

    // Opcional: observaciones
    @Column(name = "observacion_pago", length = 255)
    private String observacionPago;

    @Column(name = "observacion_anulacion", length = 255)
    private String observacionAnulacion;

    @Version
    private Long version;

    // --- Relaciones ---
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "socio_id", nullable = false)
    private Socio socio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pago_id")
    private FormaDePago formaDePago;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleFactura> detalles = new ArrayList<>();
}
