package com.example.mycar.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mycar.entities.CaracteristicaVehiculo;
import com.example.mycar.enums.EstadoVehiculo;

@Repository
public interface CaracteristicaVehiculoRepository extends BaseRepository<CaracteristicaVehiculo, Long>{
	List<CaracteristicaVehiculo> findByMarcaAndActivoTrue(String marca);
	List<CaracteristicaVehiculo> findByMarcaAndModeloAndActivoTrue(String marca, String modelo);
	List<CaracteristicaVehiculo> findByAnioAndActivoTrue(int anio);
	List<CaracteristicaVehiculo> findByCantidadPuertaAndActivoTrue(int cant);
	List<CaracteristicaVehiculo> findByCantidadAsientoAndActivoTrue(int cant);
	
	Optional<CaracteristicaVehiculo> findByMarcaAndModeloAndCantidadPuertaAndCantidadAsientoAndActivoTrue(String marca,
																							String modelo,
																							int cantPuerta,
																							int cantAsiento);
	
	@Query("""
		    SELECT cv
		    FROM CaracteristicaVehiculo cv
		    JOIN cv.vehiculo v
		    JOIN cv.costoVehiculo c
		    JOIN cv.imagen i
		    WHERE cv.activo = true
		      AND v.activo = true
		      AND c.activo = true
		      AND i.activo = true
		      AND v.estadoVehiculo = :estadoVehiculo
		""")
		List<CaracteristicaVehiculo> findByEstadoAndActivoTrue(@Param("estadoVehiculo") EstadoVehiculo estadoVehiculo);


}
