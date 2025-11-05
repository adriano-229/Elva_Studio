package com.example.mycar.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.mycar.entities.CaracteristicaVehiculo;

@Repository
public interface CaracteristicaVehiculoRepository extends BaseRepository<CaracteristicaVehiculo, Long>{
	List<CaracteristicaVehiculo> findByMarcaAndActivoTrue(String marca);
	List<CaracteristicaVehiculo> findByMarcaAndModeloAndActivoTrue(String marca, String modelo);
	List<CaracteristicaVehiculo> findByAnioAndaActivoTrue(int anio);
	List<CaracteristicaVehiculo> findByCantidadPuertaAndActivoTrue(int cant);
	List<CaracteristicaVehiculo> findByCantidadAsientoAndActivoTrue(int cant);
}
