package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.repository.DepartamentoRepository;
import com.elva.tp1.service.DepartamentoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoServiceImpl extends CrudServiceImpl<Departamento, Long> implements DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoServiceImpl(DepartamentoRepository repository) {
        super(repository);
        this.departamentoRepository = repository;
    }

    @Override
    public List<Departamento> findByProvinciaId(Long provinciaId) {
        return departamentoRepository.findByProvinciaId(provinciaId);
    }
}
