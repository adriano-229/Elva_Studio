package com.elva.tp1.service;

import com.elva.tp1.domain.Direccion;
import com.elva.tp1.repository.DireccionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DireccionService extends BaseService<Direccion, Long> {

    private final DireccionRepository direccionRepository;


    public DireccionService(DireccionRepository repository, DireccionRepository direccionRepository) {
        super(repository);
        this.direccionRepository = direccionRepository;
    }

    public List<Direccion> findAllByOrderByCalleAsc() {
        return direccionRepository.findAllByOrderByCalleAsc();
    }

    public List<Direccion> findAllByDepartamento_NombreOrderByCalleAsc(String departamentoNombre) {
        return direccionRepository.findAllByDepartamento_NombreOrderByCalleAsc(departamentoNombre);
    }


}