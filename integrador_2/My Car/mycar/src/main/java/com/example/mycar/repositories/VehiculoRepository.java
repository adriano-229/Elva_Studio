package com.example.mycar.repositories;

import java.util.Optional;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;

public interface VehiculoRepository {
	Optional<Vehiculo> findByPatenteAndActivoTrue(String Patente);
	Optional<Vehiculo> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado);
}
