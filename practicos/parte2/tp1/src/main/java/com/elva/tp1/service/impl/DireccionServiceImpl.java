package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.repository.DireccionRepository;
import com.elva.tp1.service.DireccionService;
import org.springframework.stereotype.Service;

@Service
public class DireccionServiceImpl extends CrudServiceImpl<Direccion, Long> implements DireccionService {

    public DireccionServiceImpl(DireccionRepository repository) {
        super(repository);
    }
}
