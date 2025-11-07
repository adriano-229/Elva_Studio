package com.example.mycar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alquiler")
public class Alquiler extends Base {

    @Column(name = "fecha_desde", nullable = false)
    private Date fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private Date fechaHasta;

    @Column(name = "costo_calculado", precision = 12, scale = 2)
    private BigDecimal costoCalculado; // Costo total del alquiler

    @Column(name = "cantidad_dias")
    private Integer cantidadDias; // Duración en días

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentacion_id")
    private Documentacion documentacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne(mappedBy = "alquiler")
    private DetalleFactura detalleFactura;

    @Version
    private Long version;  // Control de concurrencia optimista
}
