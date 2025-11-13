package com.projects.mycar.mycar_server.entities;

import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo extends Base {

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vehiculo", nullable = false)
    private EstadoVehiculo estadoVehiculo;

    @Column(name = "patente", nullable = false)
    private String patente;

    @ManyToOne(fetch = FetchType.EAGER) // removed cascade to avoid persisting parent from child
    @JoinColumn(name = "caracteristicaVehiculo_id")
    private CaracteristicaVehiculo caracteristicaVehiculo;

    // Eliminado campo costoVehiculo: el costo se obtiene desde caracteristicaVehiculo.getCostoVehiculo()
}
