package com.example.mycar.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;

@Repository
public interface VehiculoRepository extends BaseRepository<Vehiculo, Long>{
	Optional<Vehiculo> findByPatenteAndActivoTrue(String Patente);
	Optional<Vehiculo> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado);
}
