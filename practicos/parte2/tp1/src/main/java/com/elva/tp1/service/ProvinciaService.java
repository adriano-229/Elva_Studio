package com.elva.tp1.service;

import com.elva.tp1.domain.Provincia;
import com.elva.tp1.repository.ProvinciaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService extends BaseService<Provincia, Long> {

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaService(ProvinciaRepository repository) {
        super(repository);
        this.provinciaRepository = repository;
    }

    public List<Provincia> findAllByOrderByNombreAsc() {
        return provinciaRepository.findAllByOrderByNombreAsc();
    }

    public List<Provincia> findAllByPais_NombreOrderByNombre(String nombre) {
        return provinciaRepository.findAllByPais_NombreOrderByNombre(nombre);
    }
}
