package com.example.mycar.repositories;

import com.example.mycar.dto.reportes.AlquilerReporteDTO;
import com.example.mycar.dto.reportes.RecaudacionModeloDTO;
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

        /*@Query("""
        select new com.example.mycar.dto.reportes.AlquilerReporteDTO(
            case when cli is null then 'Sin asignar' else concat(coalesce(cli.nombre, ''), ' ', coalesce(cli.apellido, '')) end,
            cli.numeroDocumento,
            v.patente,
            cv.modelo,
            cv.marca,
            a.fechaDesde,
            a.fechaHasta,
            coalesce(f.totalPagado, 0)
        )
        from Alquiler a
        left join Cliente cli on cli.alquiler = a
        left join a.vehiculo v
        left join CaracteristicaVehiculo cv on cv.vehiculo = v
        left join a.detalleFactura df
        left join df.factura f
        where (:desde is null or a.fechaDesde >= :desde)
          and (:hasta is null or a.fechaHasta <= :hasta)
        order by a.fechaDesde asc
        """)
    List<AlquilerReporteDTO> findAlquileresPorPeriodo(@Param("desde") Date desde, @Param("hasta") Date hasta);*/

    @Query("""
            select new com.example.mycar.dto.reportes.AlquilerReporteDTO(
                case when a.cliente is null then 'Sin asignar' else concat(coalesce(a.cliente.nombre, ''), ' ', coalesce(a.cliente.apellido, '')) end,
                a.cliente.numeroDocumento,
                v.patente,
                cv.modelo,
                cv.marca,
                a.fechaDesde,
                a.fechaHasta,
                coalesce(f.totalPagado, 0)
            )
            from Alquiler a
            left join a.cliente cli
            left join a.vehiculo v
            left join v.caracteristicaVehiculo cv
            left join a.detalleFactura df
            left join df.factura f
            where (:desde is null or a.fechaDesde >= :desde)
              and (:hasta is null or a.fechaHasta <= :hasta)
            order by a.fechaDesde asc
            """)
    List<AlquilerReporteDTO> findAlquileresPorPeriodo(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);


    /*@Query("""
        select new com.example.mycar.dto.reportes.RecaudacionModeloDTO(
            coalesce(cv.modelo, 'Sin modelo'),
            coalesce(cv.marca, 'Sin marca'),
            coalesce(cv.cantidadTotalVehiculo, 0),
            count(distinct a.id),
            sum(f.totalPagado)
        )
        from Alquiler a
        left join CaracteristicaVehiculo cv on cv.vehiculo = a.vehiculo
        left join a.detalleFactura df
        left join df.factura f
        where f is not null
          and (:desde is null or f.fechaFactura >= :desde)
          and (:hasta is null or f.fechaFactura <= :hasta)
        group by cv.modelo, cv.marca, cv.cantidadTotalVehiculo
        order by sum(f.totalPagado) desc
        """)
    List<RecaudacionModeloDTO> calcularRecaudacionPorModelo(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);*/

    @Query("""
            select new com.example.mycar.dto.reportes.RecaudacionModeloDTO(
                coalesce(cv.modelo, 'Sin modelo'),
                coalesce(cv.marca, 'Sin marca'),
                coalesce(cv.cantidadTotalVehiculo, 0),
                count(distinct a.id),
                sum(f.totalPagado)
            )
            from Alquiler a
            left join a.vehiculo v
            left join v.caracteristicaVehiculo cv
            left join a.detalleFactura df
            left join df.factura f
            where f is not null
              and (:desde is null or f.fechaFactura >= :desde)
              and (:hasta is null or f.fechaFactura <= :hasta)
            group by cv.modelo, cv.marca, cv.cantidadTotalVehiculo
            order by sum(f.totalPagado) desc
            """)
    List<RecaudacionModeloDTO> calcularRecaudacionPorModelo(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta
    );

}
