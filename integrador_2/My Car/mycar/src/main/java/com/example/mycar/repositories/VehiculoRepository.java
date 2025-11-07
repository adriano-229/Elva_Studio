package com.example.mycar.repositories;

import com.example.mycar.entities.Vehiculo;
import com.example.mycar.enums.EstadoVehiculo;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends BaseRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPatenteAndActivoTrue(String Patente);

    Optional<Vehiculo> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado);
}
