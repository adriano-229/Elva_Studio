package com.example.mycar.repositories;

import com.example.mycar.entities.CaracteristicaVehiculo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaracteristicaVehiculoRepository extends BaseRepository<CaracteristicaVehiculo, Long> {
    List<CaracteristicaVehiculo> findByMarcaAndActivoTrue(String marca);

    List<CaracteristicaVehiculo> findByMarcaAndModeloAndActivoTrue(String marca, String modelo);

    List<CaracteristicaVehiculo> findByAnioAndActivoTrue(int anio);

    List<CaracteristicaVehiculo> findByCantidadPuertaAndActivoTrue(int cant);

    List<CaracteristicaVehiculo> findByCantidadAsientoAndActivoTrue(int cant);

    Optional<CaracteristicaVehiculo> findByMarcaAndModeloAndCantidadPuertaAndCantidadAsientoAndActivoTrue(String marca,
                                                                                                          String modelo,
                                                                                                          int cantPuerta,
                                                                                                          int cantAsiento);
	
	/*@Query("""
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
		List<CaracteristicaVehiculo> findByEstadoAndActivoTrue(@Param("estadoVehiculo") EstadoVehiculo estadoVehiculo);*/


    @Modifying
    @Transactional
    @Query("""
            UPDATE CaracteristicaVehiculo c
            SET 
                c.cantidadTotalVehiculo = (
                    SELECT COUNT(v) 
                    FROM Vehiculo v 
                    WHERE v.caracteristicaVehiculo = c 
                      AND v.activo = true
                ),
                c.cantidadVehiculoAlquilado = (
                    SELECT COUNT(v) 
                    FROM Vehiculo v 
                    WHERE v.caracteristicaVehiculo = c 
                      AND v.estadoVehiculo = 'Alquilado'
                      AND v.activo = true
                )
            WHERE c.activo = true
            """)
    void actualizarTotales();


}
