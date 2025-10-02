package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.repository.DireccionRepository;
import com.elva.tp1.service.DireccionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionServiceImpl extends CrudServiceImpl<Direccion, Long> implements DireccionService {

    private final DireccionRepository direccionRepository;

    public DireccionServiceImpl(DireccionRepository repository) {
        super(repository);
        this.direccionRepository = repository;
    }

    @Override
    public List<Direccion> findByLocalidadId(Long localidadId) {
        return direccionRepository.findByLocalidadId(localidadId);
    }
}
