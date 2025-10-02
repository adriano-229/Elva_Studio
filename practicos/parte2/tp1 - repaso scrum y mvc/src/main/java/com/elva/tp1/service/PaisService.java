package com.elva.tp1.service;

import com.elva.tp1.entity.Pais;
import com.elva.tp1.repository.PaisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisService extends GenericServiceImpl<Pais, Integer> {
    private final PaisRepository paisRepository; // referencia concreta

    @Autowired
    public PaisService(PaisRepository repository) {
        super(repository);
        this.paisRepository = repository;
    }

    @Override
    public List<Pais> findAllActive() {
        return paisRepository.findByActivoTrueOrderByNombreAsc();
    }
}
