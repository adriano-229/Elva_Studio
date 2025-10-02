package com.elva.tp1.service;

import com.elva.tp1.domain.Localidad;
import java.util.List;

public interface LocalidadService extends CrudService<Localidad, Long> {
    List<Localidad> findByDepartamentoId(Long departamentoId);
}
