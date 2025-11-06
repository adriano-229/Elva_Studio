package com.example.mycar.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "costo_vehiculo")
public class CostoVehiculo extends Base {

    @Column(name = "fecha_desde", nullable = false)
    private Date fechaDesde;

    @Column(name = "fecha_hasta", nullable = false)
    private Date fechaHasta;

    @Column(name = "costo", nullable = false)
    private double costo;
}
