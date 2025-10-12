package com.elva.tp1.repository;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Proveedor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProveedorRepository extends BaseRepository<Proveedor, Long> {

    List<Proveedor> findAllByDireccion(Direccion direccion);

    // Para migraciones: buscar por CUIT exacto y dirección
    Optional<Proveedor> findByCuitAndDireccion(String cuit, Direccion direccion);
}
