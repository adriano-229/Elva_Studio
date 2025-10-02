package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Departamento;
import com.elva.tp1.repository.DepartamentoRepository;
import com.elva.tp1.service.DepartamentoService;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoServiceImpl extends CrudServiceImpl<Departamento, Long> implements DepartamentoService {

    public DepartamentoServiceImpl(DepartamentoRepository repository) {
        super(repository);
    }
}
