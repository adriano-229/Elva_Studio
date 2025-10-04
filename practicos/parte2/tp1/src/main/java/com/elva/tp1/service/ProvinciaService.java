package com.elva.tp1.service;

import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;

public class ProvinciaService extends BaseService<Provincia, Long> {

    public ProvinciaService(ProvinciaRepository repository) {
        super(repository);
    }


}