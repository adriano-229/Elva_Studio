package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Localidad;
import com.elva.tp1.repository.LocalidadRepository;
import com.elva.tp1.service.LocalidadService;
import org.springframework.stereotype.Service;

@Service
public class LocalidadServiceImpl extends CrudServiceImpl<Localidad, Long> implements LocalidadService {

    public LocalidadServiceImpl(LocalidadRepository repository) {
        super(repository);
    }
}
