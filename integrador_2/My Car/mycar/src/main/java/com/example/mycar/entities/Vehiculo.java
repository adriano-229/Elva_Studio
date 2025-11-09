package com.example.mycar.entities;

import com.example.mycar.enums.EstadoVehiculo;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
    @Column(name = "estado_vehiculo",nullable = false)
	private EstadoVehiculo estadoVehiculo;
	
    @Column(name = "patente",nullable = false)
	private String patente;
    
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "caracteristicaVehiculo_id")
    private CaracteristicaVehiculo caracteristicaVehiculo;
    
    //VERIFICAR ATRIBUTO 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "costo_vehiculo_id")
    private CostoVehiculo costoVehiculo; // Relación con el costo actual del vehículo

}
