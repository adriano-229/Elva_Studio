package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.repository.PaisRepository;
import com.elva.tp1.service.PaisService;
import org.springframework.stereotype.Service;

@Service
public class PaisServiceImpl extends CrudServiceImpl<Pais, Long> implements PaisService {

    public PaisServiceImpl(PaisRepository repository) {
        super(repository);
    }
}
