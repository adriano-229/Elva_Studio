package com.elva.tp1.service;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.repository.DireccionRepository;

public class DireccionService extends BaseService<Direccion, Long> {

    public DireccionService(DireccionRepository repository) {
        super(repository);
    }


}