package com.example.mycar.repositories;

import com.example.mycar.entities.CaracteristicaVehiculo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaracteristicaVehiculoRepository extends BaseRepository<CaracteristicaVehiculo, Long> {
    List<CaracteristicaVehiculo> findByMarcaAndActivoTrue(String marca);

    List<CaracteristicaVehiculo> findByMarcaAndModeloAndActivoTrue(String marca, String modelo);

    List<CaracteristicaVehiculo> findByAnioAndActivoTrue(long anio);

    List<CaracteristicaVehiculo> findByCantidadPuertaAndActivoTrue(int cant);

    List<CaracteristicaVehiculo> findByCantidadAsientoAndActivoTrue(int cant);
}
