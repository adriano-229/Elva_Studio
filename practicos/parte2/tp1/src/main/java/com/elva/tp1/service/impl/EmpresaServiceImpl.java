package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Empresa;
import com.elva.tp1.repository.EmpresaRepository;
import com.elva.tp1.service.EmpresaService;
import org.springframework.stereotype.Service;

@Service
public class EmpresaServiceImpl extends CrudServiceImpl<Empresa, Long> implements EmpresaService {

    public EmpresaServiceImpl(EmpresaRepository repository) {
        super(repository);
    }
}
