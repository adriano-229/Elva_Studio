package com.elva.tp1.service;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.repository.DepartamentoRepository;

public class DepartamentoService extends BaseService<Departamento, Long> {

    public DepartamentoService(DepartamentoRepository repository) {
        super(repository);
    }


}