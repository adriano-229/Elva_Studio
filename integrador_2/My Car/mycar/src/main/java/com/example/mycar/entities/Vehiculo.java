package com.example.mycar.entities;

import com.example.mycar.enums.EstadoVehiculo;
import com.example.mycar.enums.RolUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehiculo")
public class Vehiculo extends Base{

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_vehiculo",nullable = false)
	private EstadoVehiculo estadovehiculo;
	
    @Column(name = "patente",nullable = false)
	private String patente;
}
