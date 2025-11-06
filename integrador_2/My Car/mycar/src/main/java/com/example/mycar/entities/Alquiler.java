package com.example.mycar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documentacion_id")
    private Documentacion documentacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @OneToOne
    @JoinColumn(name = "detalle_factura_id")
    private DetalleFactura detalleFactura;
}
