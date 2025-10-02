package com.elva.tp1.service;

import com.elva.tp1.domain.Provincia;
import java.util.List;

public interface ProvinciaService extends CrudService<Provincia, Long> {
    List<Provincia> findByPaisId(Long paisId);
}
