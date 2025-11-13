package com.example.mycar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "alquiler")
public class Alquiler extends Base {

    @Column(name = "fecha_desde", nullable = false)
    private LocalDate fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private LocalDate fechaHasta;

    //agregado
    @Column(name = "costo_calculado")
    private Double costoCalculado; // Costo total del alquiler

    //agregado
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

    //agregado
    @Version
    private Long version;  // Control de concurrencia optimista

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
