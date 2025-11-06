package com.elva.tp1.service;

import com.elva.tp1.domain.Proveedor;
import com.elva.tp1.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService extends BaseService<Proveedor, Long> {

    public ProveedorService(ProveedorRepository repository) {
        super(repository);
    }

}