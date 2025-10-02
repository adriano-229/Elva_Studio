package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Localidad;
import com.elva.tp1.repository.LocalidadRepository;
import com.elva.tp1.service.LocalidadService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadServiceImpl extends CrudServiceImpl<Localidad, Long> implements LocalidadService {

    private final LocalidadRepository localidadRepository;

    public LocalidadServiceImpl(LocalidadRepository repository) {
        super(repository);
        this.localidadRepository = repository;
    }

    @Override
    public List<Localidad> findByDepartamentoId(Long departamentoId) {
        return localidadRepository.findByDepartamentoId(departamentoId);
    }
}
