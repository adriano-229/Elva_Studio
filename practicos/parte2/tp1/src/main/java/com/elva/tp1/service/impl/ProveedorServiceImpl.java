package com.elva.tp1.service.impl;

import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.repository.ProveedorRepository;
import com.elva.tp1.service.ProveedorService;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServiceImpl extends CrudServiceImpl<Proveedor, Long> implements ProveedorService {

    public ProveedorServiceImpl(ProveedorRepository repository) {
        super(repository);
    }
}
