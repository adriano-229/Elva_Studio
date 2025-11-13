package com.projects.mycar.mycar_server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caracteristica_vehiculo")
public class CaracteristicaVehiculo extends Base {

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "cantidad_puerta", nullable = false)
    private int cantidadPuerta;

    @Column(name = "cantidad_asiento", nullable = false)
    private int cantidadAsiento;

    @Column(name = "anio", nullable = false)
    private long anio;

    @Column(name = "cantidad_total_vehiculo", nullable = false)
    private int cantidadTotalVehiculo;

    @Column(name = "cantidad_vehiculo_alquilado", nullable = false)
    private int cantidadVehiculoAlquilado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "imagen_id")
    private Imagen imagen;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "costo_vehiculo_id")
    private CostoVehiculo costoVehiculo;
}
