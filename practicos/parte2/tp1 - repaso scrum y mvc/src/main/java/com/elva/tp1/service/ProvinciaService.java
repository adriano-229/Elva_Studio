package com.elva.tp1.service;

import com.elva.tp1.entity.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProvinciaService extends GenericServiceImpl<Provincia, Integer> {
    public ProvinciaService(ProvinciaRepository repository) {
        super(repository);
    }
}

