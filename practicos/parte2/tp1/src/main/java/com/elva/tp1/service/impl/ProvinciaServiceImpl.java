package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;
import com.elva.tp1.service.ProvinciaService;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaServiceImpl extends CrudServiceImpl<Provincia, Long> implements ProvinciaService {

    public ProvinciaServiceImpl(ProvinciaRepository repository) {
        super(repository);
    }
}
