package com.elva.tp1.service;

import com.elva.tp1.domain.Direccion;

import java.util.List;

public interface DireccionService extends CrudService<Direccion, Long> {
    List<Direccion> findByLocalidadId(Long localidadId);
    // acá podés agregar métodos específicos si hacen falta
}
