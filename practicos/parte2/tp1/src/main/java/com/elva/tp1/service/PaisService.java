package com.elva.tp1.service;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.repository.PaisRepository;

public class PaisService extends BaseService<Pais, Long> {

    public PaisService(PaisRepository repository) {
        super(repository);
    }


}