package com.elva.tp1.service;

import com.elva.tp1.domain.Empresa;
import com.elva.tp1.repository.EmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BaseService<Empresa, Long> {

    public EmpresaService(EmpresaRepository repository) {
        super(repository);
    }
}
