package com.example.mycar.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.mycar.dto.recordatorios.RecordatorioDevolucionDTO;
import com.example.mycar.entities.Cliente;

@Repository
public interface ClienteRepository extends BaseRepository<Cliente, Long> {

    @Query("""
        select new com.example.mycar.dto.recordatorios.RecordatorioDevolucionDTO(
            cli.id,
            concat(coalesce(cli.nombre, ''), ' ', coalesce(cli.apellido, '')),
            contactoCorreo.email,
            a.fechaDesde,
            a.fechaHasta,
            cv.marca,
            cv.modelo,
            v.patente
        )
        from Cliente cli
        join cli.alquiler a
        left join cli.contactoCorreo contactoCorreo
        left join a.vehiculo v
        left join CaracteristicaVehiculo cv on cv.vehiculo = v
        where (:clienteId is null or cli.id = :clienteId)
          and cli.activo = true
          and a.activo = true
        """)
    List<RecordatorioDevolucionDTO> findClientesConAlquilerActivo(@Param("clienteId") Long clienteId);

    @Query("""
        select new com.example.mycar.dto.recordatorios.RecordatorioDevolucionDTO(
            cli.id,
            concat(coalesce(cli.nombre, ''), ' ', coalesce(cli.apellido, '')),
            contactoCorreo.email,
            a.fechaDesde,
            a.fechaHasta,
            cv.marca,
            cv.modelo,
            v.patente
        )
        from Cliente cli
        join cli.alquiler a
        left join cli.contactoCorreo contactoCorreo
        left join a.vehiculo v
        left join CaracteristicaVehiculo cv on cv.vehiculo = v
        where a.fechaHasta = :fecha
          and cli.activo = true
          and a.activo = true
        """)
    List<RecordatorioDevolucionDTO> findClientesConDevolucionEn(@Param("fecha") Date fecha);

    @Query("""
        select ct.telefono
        from Cliente cli
        join cli.contactoTelefonico ct
        where cli.id = :clienteId
          and cli.activo = true
          and ct.activo = true
        """)
    Optional<String> findTelefonoMovilByClienteId(@Param("clienteId") Long clienteId);
}
