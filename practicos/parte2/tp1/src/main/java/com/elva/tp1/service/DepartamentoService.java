package com.elva.tp1.service;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.repository.DepartamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService extends BaseService<Departamento, Long> {

    private final DepartamentoRepository departamentoRepository;


    public DepartamentoService(DepartamentoRepository repository, DepartamentoRepository departamentoRepository) {
        super(repository);
        this.departamentoRepository = departamentoRepository;
    }

    public List<Departamento> findAllByOrderByNombreAsc() {
        return departamentoRepository.findAllByOrderByNombreAsc();
    }

    public List<Departamento> findAllByProvincia_NombreOrderByNombre(String nombre) {
        return departamentoRepository.findAllByProvincia_NombreOrderByNombre(nombre);
    }


}