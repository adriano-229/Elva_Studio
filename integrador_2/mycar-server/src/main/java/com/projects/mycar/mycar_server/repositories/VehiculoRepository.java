package com.projects.mycar.mycar_server.repositories;

import com.projects.mycar.mycar_server.entities.Vehiculo;
import com.projects.mycar.mycar_server.enums.EstadoVehiculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends BaseRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPatenteAndActivoTrue(String Patente);

    List<Vehiculo> findByEstadoVehiculoAndActivoTrue(EstadoVehiculo estado);

    @Query("""
            SELECT v
            FROM Vehiculo v
            WHERE v.activo = true and v.id NOT IN (
                SELECT a.vehiculo.id
                FROM Alquiler a
                WHERE (
                	a.activo = true 
                	AND :fechaDesde <= a.fechaHasta
                    AND :fechaHasta >= a.fechaDesde
                )
            )
            """)
    List<Vehiculo> findVehiculosDisponibles(
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );

    @Query("""
            SELECT DISTINCT a.vehiculo
            FROM Alquiler a
            WHERE a.activo = true
            AND (
                :fechaDesde <= a.fechaHasta
                AND :fechaHasta >= a.fechaDesde
            )
            """)
    List<Vehiculo> findVehiculosAlquilados(
            @Param("fechaDesde") LocalDate fechaDesde,
            @Param("fechaHasta") LocalDate fechaHasta
    );

    @Query("""
                SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END
                FROM Vehiculo v
                WHERE v.caracteristicaVehiculo.id = :caracteristicaId
                  AND v.activo = true
            """)
    boolean existeVehiculoActivoPorCaracteristica(@Param("caracteristicaId") Long caracteristicaId);


}
