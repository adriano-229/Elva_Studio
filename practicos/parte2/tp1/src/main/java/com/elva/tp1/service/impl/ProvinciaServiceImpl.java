package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;
import com.elva.tp1.service.ProvinciaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaServiceImpl extends CrudServiceImpl<Provincia, Long> implements ProvinciaService {

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaServiceImpl(ProvinciaRepository repository) {
        super(repository);
        this.provinciaRepository = repository;
    }

    @Override
    public List<Provincia> findByPaisId(Long paisId) {
        return provinciaRepository.findByPaisId(paisId);
    }
}
