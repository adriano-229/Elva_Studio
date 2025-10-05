package com.elva.tp1.service;

import com.elva.tp1.domain.Pais;
import com.elva.tp1.repository.PaisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaisService extends BaseService<Pais, Long> {

    private final PaisRepository paisRepository;


    public PaisService(PaisRepository repository) {
        super(repository);
        this.paisRepository = repository;
    }

    public List<Pais> findAllByOrderByNombreAsc() {
        return paisRepository.findAllByOrderByNombreAsc();
    }

}