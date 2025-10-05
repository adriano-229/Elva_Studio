package com.elva.tp1.repository;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.domain.Proveedor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProveedorRepository extends BaseRepository<Proveedor, Long> {

    List<Proveedor> findAllByCuitContainsOrderByCuit(String cuit);

    List<Proveedor> findAllByDireccion(Direccion direccion);
}
