package com.example.mycar.repositories;

import com.example.mycar.dto.reportes.AlquilerReporteDTO;
import com.example.mycar.dto.reportes.RecaudacionModeloDTO;
import com.example.mycar.entities.Alquiler;
import java.util.Date;

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
    List<AlquilerReporteDTO> findAlquileresPorPeriodo(@Param("desde") Date desde, @Param("hasta") Date hasta);

    @Query("""
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
    List<RecaudacionModeloDTO> calcularRecaudacionPorModelo(@Param("desde") LocalDate desde, @Param("hasta") LocalDate hasta);

}
