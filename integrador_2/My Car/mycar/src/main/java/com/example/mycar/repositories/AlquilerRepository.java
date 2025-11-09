package com.example.mycar.repositories;

import com.example.mycar.entities.Alquiler;
import com.example.mycar.entities.Vehiculo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlquilerRepository extends BaseRepository<Alquiler, Long> {

    List<Alquiler> findByDetalleFacturaIsNullAndActivoTrue();

    List<Alquiler> findByIdInAndActivoTrue(List<Long> ids);

    Optional<Alquiler> findByIdAndDetalleFacturaIsNullAndActivoTrue(Long id);
    
    @Query("""
            SELECT a 
            FROM Alquiler a
            WHERE a.activo = true
            AND (
                (a.fechaDesde BETWEEN :fechaInicio AND :fechaFin)
                OR (a.fechaHasta BETWEEN :fechaInicio AND :fechaFin)
                OR (:fechaInicio BETWEEN a.fechaDesde AND a.fechaHasta)
                OR (:fechaFin BETWEEN a.fechaDesde AND a.fechaHasta)
            )
            ORDER BY a.fechaDesde
            """)
    List<Alquiler> findAlquileresEnPeriodo(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );
    
    @Query("""
            SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END
            FROM Alquiler a
            WHERE a.vehiculo = :vehiculo
              AND a.vehiculo.activo = true
              AND a.activo = true
              AND :fechaActual BETWEEN a.fechaDesde AND a.fechaHasta
            """)
    boolean existeAlquilerActivoEnFecha(
        @Param("vehiculo") Vehiculo vehiculo,
        @Param("fechaActual") LocalDate fechaActual
    );
    
    List<Alquiler> findByActivoTrueAndCliente_ActivoTrueAndCliente_Id(Long idCliente);
}
