package com.example.mycar.entities;

import com.example.mycar.enums.EstadoFactura;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "factura")
public class Factura extends Base {

    @Column(name = "numero_factura", nullable = false)
    private long numeroFactura;

    @Column(name = "fecha_factura", nullable = false)
    private LocalDate fechaFactura;

    @Column(name = "total_pagado", nullable = false)
    private Double totalPagado = 0.0;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoFactura estado = EstadoFactura.Sin_definir;

    // Opcional: observaciones
    @Column(name = "observacion_pago", length = 255)
    private String observacionPago;

    @Column(name = "observacion_anulacion", length = 255)
    private String observacionAnulacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forma_pago_id")
    private FormaDePago formaDePago;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<DetalleFactura> detalles = new ArrayList<>();
}
